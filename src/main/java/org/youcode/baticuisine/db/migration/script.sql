CREATE TYPE projectstate AS ENUM ('INPROGRESS', 'COMPLETED', 'SUSPENDED');
CREATE TYPE componentType AS ENUM ('MATERIALS', 'WORKFORCE');

CREATE TABLE clients (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    adresse VARCHAR(100) NOT NULL,
    telephone VARCHAR(50),
    isProfessional BOOLEAN
);

CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(50) NOT NULL,
    profitMargin DOUBLE PRECISION,
    totalCost DOUBLE PRECISION,
    projectState projectstate,
    clientId UUID,
    FOREIGN KEY (clientId) REFERENCES clients(id)
);

CREATE TABLE estimates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    estimatedAmount DOUBLE PRECISION,
    estimatedDate DATE,
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
    projectId UUID,
    FOREIGN KEY (projectId) REFERENCES projects(id)
);

CREATE TABLE materials (
    unitaryCost DOUBLE PRECISION,
    transportCost DOUBLE PRECISION,
    qualityFactor DOUBLE PRECISION
) INHERITS (component);

CREATE TABLE workforce (
    hourlyRate DOUBLE PRECISION,
    workHours DOUBLE PRECISION,
    productiveWorker DOUBLE PRECISION
) INHERITS (component);
