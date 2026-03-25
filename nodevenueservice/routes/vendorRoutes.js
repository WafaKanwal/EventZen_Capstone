const express = require('express');
const router = express.Router();
const VendorService = require('../services/VendorService');
const auth = require('../middleware/auth');

router.post('/', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const vendor = await VendorService.createVendor(req.body);
        res.status(201).json(vendor);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.get('/', auth, async (req, res) => {
    try {
        const vendors = await VendorService.getAllVendors();
        res.json(vendors);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.get('/:id', auth, async (req, res) => {
    try {
        const vendor = await VendorService.getVendorById(req.params.id);
        if (!vendor) return res.status(404).json({ message: 'Vendor not found' });
        res.json(vendor);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.put('/:id', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const updatedVendor = await VendorService.updateVendor(req.params.id, req.body);
        if (!updatedVendor) return res.status(404).json({ message: 'Vendor not found' });
        res.json(updatedVendor);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

router.delete('/:id', auth, async (req, res) => {
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ message: "Access Denied" });
    }

    try {
        const deleted = await VendorService.deleteVendor(req.params.id);
        if (!deleted) return res.status(404).json({ message: 'Vendor not found' });
        res.json({ message: 'Vendor deleted successfully' });
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;