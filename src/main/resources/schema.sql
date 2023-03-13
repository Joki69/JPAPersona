CREATE TABLE arcana (
id_arcana serial NOT NULL,
nombre character varying(1000) NOT NULL,
CONSTRAINT pk_arcana PRIMARY KEY(id_arcana)
);


CREATE TABLE debilidad (
id_debilidad serial NOT NULL,
nombre_debilidad character varying(2000) NOT NULL,
CONSTRAINT pk_debilidad PRIMARY KEY(id_debilidad)
);

CREATE TABLE persona (
id_persona serial NOT NULL,
id_arcana integer NOT NULL,
id_debilidad integer NOT NULL,
nombre_arcana character varying(100)NOT NULL,
nombre_persona character varying(100)NOT NULL,
historia character varying(3000)NOT NULL,
CONSTRAINT pk_persona PRIMARY KEY(id_persona),
   CONSTRAINT fk_arcana
      FOREIGN KEY(id_arcana) 
	  REFERENCES arcana(id_arcana)
	  MATCH SIMPLE
	  ON UPDATE NO ACTION ON DELETE NO ACTION,
   CONSTRAINT fk_debilidad
      FOREIGN KEY(id_debilidad)
      REFERENCES debilidades(id_debilidad)
      MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
);

