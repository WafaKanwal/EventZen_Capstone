const Vendor = require('../models/Vendor');

class VendorService {

    async createVendor(vendorData) {
        return await Vendor.create(vendorData);
    }

    async getAllVendors() {
        return await Vendor.findAll();
    }

    async getVendorById(id) {
        return await Vendor.findByPk(id);
    }

    async updateVendor(id, vendorData) {
        const vendor = await Vendor.findByPk(id);
        if (!vendor) return null;

        return await vendor.update(vendorData);
    }

    async deleteVendor(id) {
        const vendor = await Vendor.findByPk(id);
        if (!vendor) return null;

        await vendor.destroy();
        return true;
    }
}

module.exports = new VendorService();