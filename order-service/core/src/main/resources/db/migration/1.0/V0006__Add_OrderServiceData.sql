INSERT INTO Item(id, ModificationCounter, name, description, price) VALUES (21, 0, 'spaghetti bolognese', 'Italy', 250);

INSERT INTO Customer(id, ModificationCounter, firstname, lastname) VALUES (31, 0, 'John', 'Travolta');

INSERT INTO OrderSummary(id, ModificationCounter, price, ownerId, creationDate, status) VALUES (41, 0, 671.10, 31, '2019-03-15', 'SERVED');

INSERT INTO OrderPosition(orderId, itemId) VALUES (41, 21);