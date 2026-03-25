const express = require('express');
const router = express.Router();
const VenueService = require('../services/VenueService');
const auth = require('../middleware/auth');

router.post('/', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const venue = await VenueService.createVenue(req.body);
        res.status(201).json(venue);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.get('/', auth, async (req, res) => {
    try {
        const { location, capacity } = req.query;
        if (location && capacity) {
            const venues = await VenueService.searchVenues(location, Number(capacity));
            return res.json(venues);
        }
        const venues = await VenueService.getAllVenues();
        res.json(venues);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.get('/:id', auth, async (req, res) => {
    try {
        const venue = await VenueService.getVenueById(req.params.id);
        if (!venue) return res.status(404).json({ message: 'Venue not found' });
        res.json(venue);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.put('/:id', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const updatedVenue = await VenueService.updateVenue(req.params.id, req.body);
        if (!updatedVenue) return res.status(404).json({ message: 'Venue not found' });
        res.json(updatedVenue);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.delete('/:id', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const deleted = await VenueService.deleteVenue(req.params.id);
        if (!deleted) return res.status(404).json({ message: 'Venue not found' });
        res.json({ message: 'Venue deleted successfully' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.get('/:id/availability', auth, async (req, res) => {
    try {
        const available = await VenueService.checkAvailability(req.params.id);
        if (available) return res.json({ message: 'Venue available' });
        res.status(404).json({ message: 'Venue not found' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;