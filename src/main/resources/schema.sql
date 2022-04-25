CREATE SCHEMA IF NOT EXISTS public;
--persona
DROP TABLE IF EXISTS  movimientos;
DROP TABLE IF EXISTS  cuenta;
DROP TABLE IF EXISTS  cliente;
DROP TABLE IF EXISTS  persona;
CREATE TABLE IF NOT EXISTS public.persona
    (
        persona_id UUID NOT NULL,
        identificacion         CHARACTER VARYING(255) NOT NULL,
        nombre                 CHARACTER VARYING(255) NOT NULL,
        genero                 CHARACTER VARYING(255) NOT NULL,
        edad	               INTEGER  NOT NULL,
        telefono               CHARACTER VARYING(255) NOT NULL,
        direccion			   CHARACTER VARYING(255)  NOT NULL,
        CONSTRAINT persona_id_pkey PRIMARY KEY (persona_id),
        CONSTRAINT uk_persona_identificacion UNIQUE (identificacion)
    );
 --cliente

 create table IF NOT EXISTS public.cliente
 (
  		cliente_id UUID NOT NULL,
        password              CHARACTER VARYING(255) NOT NULL,
        estado               	BOOLEAN DEFAULT true NOT NULL,
        PRIMARY KEY (cliente_id),
        CONSTRAINT fk_persona_cliente FOREIGN KEY (cliente_id) REFERENCES "persona" ("persona_id")
 );
--cuenta

create table IF NOT EXISTS public.cuenta
 (
  		cuenta_id UUID NOT NULL,
        numero_cuenta              CHARACTER VARYING(255) NOT NULL,
        tipo_cuenta                CHARACTER VARYING(255) NOT NULL,
        saldo_inicial			   numeric(16, 2) NOT NULL DEFAULT 0.00,
        estado					   BOOLEAN DEFAULT true NOT null,
        cliente_id				   UUID NOT null,
        limite_diario 			   numeric(16, 2) null DEFAULT 0.00,
        PRIMARY KEY (cuenta_id),
        CONSTRAINT uk_cuenta_numero_cuenta UNIQUE (numero_cuenta),
        CONSTRAINT fk_cuenta_cliente FOREIGN KEY (cliente_id) REFERENCES "cliente" ("cliente_id")
 );

--cuenta

create table IF NOT EXISTS public.movimientos
 (
  		movimiento_id UUID NOT NULL,
        tipo_movimiento              CHARACTER VARYING(255) NOT NULL,
        fecha                        TIMESTAMP(6) WITHOUT TIME ZONE DEFAULT now(),
        valor			   			 numeric(16, 2) NOT NULL DEFAULT 0.00,
        saldo					     numeric(16, 2) NOT NULL DEFAULT 0.00,
        cuenta_id  					 UUID not null,
        PRIMARY KEY (movimiento_id),
        CONSTRAINT fk_movimientos_cuenta FOREIGN KEY (cuenta_id) REFERENCES "cuenta" ("cuenta_id")
 );
