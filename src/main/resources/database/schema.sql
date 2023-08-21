-- schema per le tabelle del database

drop table IF EXISTS ads;
drop table IF EXISTS advertisers;
drop table IF EXISTS clients;
drop table IF EXISTS bookings;
drop table IF EXISTS favorites;

create TABLE IF NOT EXISTS ads (
    id INTEGER PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    address TEXT NOT NULL,
    city TEXT NOT NULL,
    price INTEGER NOT NULL,
    sqrmt INTEGER NOT NULL,
    sell BOOLEAN NOT NULL,
    advertiser_id INTEGER NOT NULL,
    FOREIGN KEY (advertiser_id) REFERENCES advertisers(id) ON delete CASCADE
);

create TABLE IF NOT EXISTS bookings (
    id INTEGER PRIMARY KEY,
    date DATE NOT NULL,
    time TIME NOT NULL,
    id_ad INTEGER NOT NULL,
    id_client TEXT NOT NULL,
    FOREIGN KEY (id_ad) REFERENCES ads(id) ON delete CASCADE,
    FOREIGN KEY (id_client) REFERENCES clients(fiscal_code) ON delete CASCADE
);

create TABLE IF NOT EXISTS clients (
    fiscal_code TEXT PRIMARY KEY,
    name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    budget INTEGER NOT NULL
);

create TABLE IF NOT EXISTS advertisers (
    id INTEGER PRIMARY KEY,
    bank_account INTEGER NOT NULL,
    agency_name TEXT,
    agency_fee INTEGER,
    private_owners_name TEXT,
    private_owners_last_name TEXT
);

create TABLE IF NOT EXISTS favorites (
    id_ad INTEGER NOT NULL,
    id_client TEXT NOT NULL,
    PRIMARY KEY (id_ad, id_client),
    FOREIGN KEY (id_ad) REFERENCES ads(id) ON delete CASCADE,
    FOREIGN KEY (id_client) REFERENCES clients(fiscal_code) ON delete CASCADE
);
