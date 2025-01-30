--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.9
-- Dumped by pg_dump version 9.4.9
-- Started on 2019-04-22 19:09:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 179 (class 1259 OID 154183)
-- Name: currency; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE currency (
    currencyid smallint NOT NULL,
    currency character varying,
    shortname character varying,
    sign character varying,
    code character varying,
    satcode character varying,
    decimals smallint
);


ALTER TABLE currency OWNER TO postgres;

--
-- TOC entry 2065 (class 0 OID 154183)
-- Dependencies: 179
-- Data for Name: currency; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY currency (currencyid, currency, shortname, sign, code, satcode, decimals) FROM stdin;
1	Pesos	M.N.	$	\N	MXN	2
2	USD	USD	$	\N	USD	2
\.


--
-- TOC entry 1955 (class 2606 OID 154278)
-- Name: currency_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (currencyid);


-- Completed on 2019-04-22 19:09:22

--
-- PostgreSQL database dump complete
--

-- agregamos tipo de cambio de banxico 13/05/2019
alter table currency ADD primary key (currencyid);

CREATE TABLE exchangerate
(
  exchangerateid integer NOT NULL,
  currencyid smallint,
  exchangerate numeric(10,6),
  date timestamp without time zone,
  CONSTRAINT exchangerate_pkey PRIMARY KEY (exchangerateid),
  CONSTRAINT "$1" FOREIGN KEY (currencyid)
      REFERENCES currency (currencyid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION DEFERRABLE INITIALLY DEFERRED
)
WITH (
  OIDS=FALSE
);
ALTER TABLE exchangerate
  OWNER TO postgres;


CREATE SEQUENCE exchangerate_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE exchangerate_seq
  OWNER TO postgres;
  
alter table users add column currencyid  smallint;
