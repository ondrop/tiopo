import mb from 'mountebank';
import currencyRateService from './currency-rate-service.js';
import settings from './settings.js';

const mbServerInstance = mb.create({
    port: settings.port,
    pidfile: '../mb.pid',
    logfile: '../mb.log',
    protofile: '../protofile.json',
    ipWhitelist: ['*']
});

mbServerInstance.then(function() {
    currencyRateService.addService().then(r => r);
});