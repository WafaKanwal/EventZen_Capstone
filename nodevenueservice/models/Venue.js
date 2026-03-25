const { DataTypes } = require('sequelize');
const sequelize = require('../db');

const Venue = sequelize.define('Venue', {
    id: {
        type: DataTypes.BIGINT,
        autoIncrement: true,
        primaryKey: true
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false
    },
    location: {
        type: DataTypes.STRING
    },
    capacity: {
        type: DataTypes.INTEGER
    },
    price: {
        type: DataTypes.DOUBLE
    }
}, {
    tableName: 'venues', 
    timestamps: false
});

module.exports = Venue;