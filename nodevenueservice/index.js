const express = require('express');
const sequelize = require('./db');
require('dotenv').config();
const cors = require('cors');

const app = express();
const PORT = process.env.PORT || 8084;

app.use(cors({
    origin: 'http://localhost:3000', 
    methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
    allowedHeaders: ['Content-Type', 'Authorization']
}));
app.use(express.json()); 
app.use(express.urlencoded({ extended: true }));

const Vendor = require('./models/Vendor');
const Venue = require('./models/Venue');

const vendorRoutes = require('./routes/vendorRoutes');
const venueRoutes = require('./routes/venueRoutes');

app.use('/vendors', vendorRoutes);
app.use('/venues', venueRoutes);

app.get('/', (req, res) => {
  res.send('Node Venue Service Running');
});

sequelize.authenticate()
  .then(() => console.log('Database connected successfully!'))
  .then(() => sequelize.sync({ alter: true })) 
  .then(() => console.log('Tables synced successfully!'))
  .catch(err => console.error('Unable to connect to the database or sync tables:', err));

app.listen(PORT, () => console.log(`Server running on port ${PORT}`));