

--DATA PARA TABLA PERSONA
INSERT INTO public.persona (persona_id, identificacion, nombre, genero, edad, telefono, direccion)
VALUES('6d125956-cf74-4d10-a282-78ca12ffac8f'::uuid, '1303753618', 'Jose Lema', 'Masculino', 38, '098254785', 'Otavalo sn y principal');

INSERT INTO public.persona (persona_id, identificacion, nombre, genero, edad, telefono, direccion)
VALUES('9c5aedbf-ca40-4664-9d19-0b163b4f87a6'::uuid, '0200982163', 'Marianela Montalvo', 'Femenino', 25, '097548965', 'Amazonas y NNUU');

INSERT INTO public.persona (persona_id, identificacion, nombre, genero, edad, telefono, direccion)
VALUES('9ac9f435-2b43-4569-93fa-8bde5720877d'::uuid, '1713580221', 'Juan Osorio', 'Masculino', 45, '098874587', '13 junio y Equinoccial');
 --PARA CLIENTES
 --Jose lema
INSERT INTO public.cliente (cliente_id, password, estado) VALUES('6d125956-cf74-4d10-a282-78ca12ffac8f'::uuid, '1234', TRUE);
--Marianela montalvo
INSERT INTO public.cliente (cliente_id, password, estado) VALUES('9c5aedbf-ca40-4664-9d19-0b163b4f87a6'::uuid, '5678',TRUE);
--Juan osoario
INSERT INTO public.cliente (cliente_id, password, estado) VALUES('9ac9f435-2b43-4569-93fa-8bde5720877d'::uuid, '1245',TRUE);

--para cuentas
--Jose lema
INSERT INTO public.cuenta (cuenta_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado,limite_diario, cliente_id)
VALUES('720a19af-6c10-4be9-b895-1a4a1648d002'::uuid, '478758', 'Ahorro', 2000.00, true, 1000.00,'6d125956-cf74-4d10-a282-78ca12ffac8f'::uuid);
--marianela montalvo
INSERT INTO public.cuenta (cuenta_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado,limite_diario, cliente_id)
VALUES('522d927c-171b-4af4-ac64-1d909ae3fc58'::uuid, '225487', 'Corriente', 100.00, true,1000.00, '9c5aedbf-ca40-4664-9d19-0b163b4f87a6'::uuid);
--Juan osorio
INSERT INTO public.cuenta (cuenta_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado,limite_diario, cliente_id)
VALUES('6a9a1746-0410-43a2-b89d-9683371ee985'::uuid, '495878', 'Ahorro', 0.00, true,1000.00, '9ac9f435-2b43-4569-93fa-8bde5720877d'::uuid);
--Marianela Montalvo cuenta ahorro
INSERT INTO public.cuenta (cuenta_id, numero_cuenta, tipo_cuenta, saldo_inicial, estado,limite_diario, cliente_id)
VALUES('cee0b2e0-ad74-4258-88e6-cde92565f650'::uuid, '496825', 'Ahorro', 540.00, true,1000.00, '9c5aedbf-ca40-4664-9d19-0b163b4f87a6'::uuid);