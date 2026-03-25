const Venue = require('../models/Venue');
const { Op } = require('sequelize');

class VenueService {

    async createVenue(venueData) {
        return await Venue.create(venueData);
    }

    async getAllVenues() {
        return await Venue.findAll();
    }

    async getVenueById(id) {
        return await Venue.findByPk(id);
    }

    async updateVenue(id, venueData) {
        const venue = await Venue.findByPk(id);
        if (!venue) return null;

        return await venue.update(venueData);
    }

    async deleteVenue(id) {
        const venue = await Venue.findByPk(id);
        if (!venue) return null;

        await venue.destroy();
        return true;
    }

    async searchVenues(location, capacity) {
        return await Venue.findAll({
            where: {
                location: location,
                capacity: { [Op.gte]: capacity }
            }
        });
    }

    async checkAvailability(id) {
        const venue = await Venue.findByPk(id);
        return venue ? true : false;
    }
}

module.exports = new VenueService();