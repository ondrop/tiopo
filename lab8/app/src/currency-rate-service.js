import mbHelper from './mountebank-helper.js';
import settings from './settings.js';

function addService() {

    const USD = {
        "timestamp": 1637782574,
        "source": "USD",
        "quotes": {
            "USDRUB": 74.98,
            "USDEUR": 0.89
        }
    }

    const RUB = {
        "timestamp": 1637782574,
        "source": "RUB",
        "quotes": {
            "RUBUSD": 0.013,
            "RUBEUR": 0.012
        }
    }

    const EUR = {
        "timestamp": 1637782574,
        "source": "EUR",
        "quotes": {
            "EURRUB": 83.95,
            "EURUSD": 1.12
        }
    }

    const CURRENCIES = {
        USD, RUB, EUR
    }

    const stubs = [
        {
            predicates: [ {
                equals: {
                    method: "GET",
                    path: "/currencies"
                }
            }],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(CURRENCIES)
                    }
                }
            ]
        },
        {
            predicates: [ {
                equals: {
                    method: "GET",
                    path: "/currencies/USD"
                }
            }],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(USD)
                    }
                }
            ]
        },
        {
            predicates: [ {
                equals: {
                    method: "GET",
                    path: "/currencies/RUB"
                }
            }],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(RUB)
                    }
                }
            ]
        },
        {
            predicates: [ {
                equals: {
                    method: "GET",
                    "path": "/currencies/EUR"
                }
            }],
            responses: [
                {
                    is: {
                        statusCode: 200,
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(EUR)
                    }
                }
            ]
        },
    ];

    const imposter = {
        port: settings.currency_service_port,
        protocol: 'http',
        stubs: stubs
    };

    return mbHelper.postImposter(imposter);
}

export default { addService };