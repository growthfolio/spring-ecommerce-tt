-- USE db_ecommerce

-- LIST ALL ?
SELECT * FROM users;

-- UPDATE ITEM
UPDATE users
SET type = 'admin'
WHERE id = 1;


-- USERS
INSERT INTO users (name, email, password, cpf_cnpj, type, photo) VALUES
('Alice Silva', 'alice@gmail.com', 'password123', '12345678901', 'customer', 'https://example.com/photo1.jpg'),
('Bob Santos', 'bob@gmail.com', 'password456', '98765432100', 'admin', 'https://example.com/photo2.jpg');

-- CATEGORIES
INSERT INTO category (name, description, photo) VALUES
('Ferramentas', 'Ferramentas manuais e elétricas', 'https://example.com/tools.jpg'),
('Tintas', 'Tintas de várias cores e usos', 'https://example.com/paints.jpg'),
('Materiais Elétricos', 'Fios, cabos e equipamentos para instalações elétricas.', 'https://example.com/electrical.jpg'),
('EPI', 'Equipamentos de Proteção Individual.', 'https://example.com/epi.jpg'),
('Construção', 'Materiais e ferramentas para construção civil.', 'https://example.com/construction.jpg'),
('Lonas', 'Lonas plásticas para proteção e cobertura.', 'https://example.com/lonas.jpg'),
('Hidráulica', 'Tubos, conexões e outros materiais hidráulicos.', 'https://example.com/hydraulics.jpg'),
('Ferragens', 'Ferragens diversas para construções e reformas.', 'https://example.com/ferragens.jpg'),
('Iluminação', 'Lâmpadas, refletores e acessórios de iluminação.', 'https://example.com/lighting.jpg'),
('Jardinagem', 'Ferramentas e acessórios para jardinagem.', 'https://example.com/gardening.jpg');

-- PRODUCTS
INSERT INTO products (name, price, amount, description, photo, sales, likes, category_id,  created_at) VALUES
('Martelo', 25.99, 100, 'Martelo de alta durabilidade, ideal para construção civil e uso doméstico.', 'https://i.imgur.com/JGqZvD0.jpeg', 50, 10, 1, CURRENT_TIMESTAMP),
('Tinta Azul', 49.99, 200, 'Tinta azul premium com alta cobertura, perfeita para pintura de interiores e exteriores.', 'https://i.imgur.com/asiRcTH.jpeg', 20, 5, 2, CURRENT_TIMESTAMP),
('Fio de Cobre', 79.99, 50, 'Fio de cobre puro com excelente condutividade, ideal para instalações elétricas seguras.', 'https://i.imgur.com/o4n6uHN.jpeg', 10, 5, 3,  CURRENT_TIMESTAMP),
('Luva de Segurança', 15.99, 200, 'Luva de segurança reforçada, garantindo proteção e conforto no manuseio de ferramentas.', 'https://i.imgur.com/oEk4vQJ.jpeg', 30, 12, 4, CURRENT_TIMESTAMP),
('Cimento Queimado', 25.00, 100, 'Cimento queimado Suvinil, ideal para acabamentos modernos e sofisticados.', 'https://i.imgur.com/tf72Ovl.jpeg', 60, 20, 5, CURRENT_TIMESTAMP),
('Lona Plástica Azul', 89.90, 150, 'Lona plástica azul ultra resistente, indicada para coberturas, reformas e proteção.', 'https://i.imgur.com/nwBLFe3.jpeg', 25, 8, 6, CURRENT_TIMESTAMP),
('Tubo de PVC', 12.50, 300, 'Tubo de PVC padrão para sistemas hidráulicos, garantindo durabilidade e fácil instalação.', 'https://i.imgur.com/UgrwYJn.jpeg', 45, 18, 7,  CURRENT_TIMESTAMP),
('Eco-Primer', 8.99, 500, 'Eco-Primer de rápida secagem, ideal para preparar superfícies antes da pintura.', 'https://i.imgur.com/XluF48U.jpeg', 15, 3, 8, CURRENT_TIMESTAMP),
('Refletor LED 50W', 129.99, 80, 'Refletor LED 50W com alta eficiência luminosa, perfeito para iluminação externa e áreas amplas.', 'https://i.imgur.com/QfURZfe.jpeg', 40, 25, 9, CURRENT_TIMESTAMP),
('Torneira Esfera Fluida', 25.99, 120, 'Torneira esfera de 1/2", modelo fluida, prática e eficiente para instalações hidráulicas.', 'https://i.imgur.com/oe1WWyP.jpeg', 20, 10, 10, CURRENT_TIMESTAMP);

-- CART
INSERT INTO carts (user_id, updatedAt, createdAt) VALUES
(1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
(2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- CART ITEM
INSERT INTO cart_items (cart_id, product_id, quantity, unitPrice) VALUES
(1, 1, 2, 25.99),
(1, 2, 3, 49.99),
(2, 2, 1, 49.99);
