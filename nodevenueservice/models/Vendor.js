const { DataTypes } = require("sequelize");
const sequelize = require("../db");

const Vendor = sequelize.define(
  "Vendor",
  {
    id: {
      type: DataTypes.BIGINT,
      autoIncrement: true,
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    serviceType: {
      type: DataTypes.STRING,
    },
    contact: {
      type: DataTypes.STRING,
    },
    price: {
      type: DataTypes.DOUBLE,
    },
  },
  {
    tableName: "vendors",
    timestamps: false,
  },
);

module.exports = Vendor;
