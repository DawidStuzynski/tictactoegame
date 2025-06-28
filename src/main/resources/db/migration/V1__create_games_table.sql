-- Create games table with UUID as the primary key
CREATE TABLE games (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    board JSONB NOT NULL DEFAULT '[]',
    current_player VARCHAR NOT NULL DEFAULT 'X',
    status VARCHAR NOT NULL DEFAULT 'IN_PROGRESS',
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
