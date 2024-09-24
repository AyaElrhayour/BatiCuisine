CREATE TYPE projectState AS ENUM ('PENDING', 'COMPLETED', 'CANCELLED');
CREATE TYPE componentType AS ENUM ('MATERIALS', 'WORKFORCE');

CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    telephone VARCHAR(50),
    isProfessional BOOLEAN
);

CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    profitMargin DOUBLE PRECISION,
    totalCost DOUBLE PRECISION,
    projectState ProjectState,
    clientId UUID,
    FOREIGN KEY (clientId) REFERENCES clients(id)
);

CREATE TABLE estimates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estimatedAmount DOUBLE PRECISION,
    issuedDate DATE,
    validityDate DATE,
    accepted BOOLEAN,
    projectId UUID,
    FOREIGN KEY (projectId) REFERENCES projects(id)
);

CREATE TABLE component (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    componentType componentType,
    name VARCHAR(50) NOT NULL,
    tvaRate DOUBLE PRECISION,
    unitaryCost DOUBLE PRECISION,
    quantity DOUBLE PRECISION,
    outputFactor DOUBLE PRECISION,
    projectId UUID,
    FOREIGN KEY (projectId) REFERENCES projects(id)
);

CREATE TABLE materials (
    transportCost DOUBLE PRECISION
) INHERITS (component);

CREATE TABLE workforce (
) INHERITS (component);
