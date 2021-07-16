--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: client_template; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA client_template;


--
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


SET search_path = client_template, pg_catalog;

--
-- Name: complete_entity_search_count; Type: TYPE; Schema: client_template; Owner: -
--

CREATE TYPE complete_entity_search_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	template text,
	edit_template text,
	lock_mode boolean,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_datatype_name character varying,
	attribute_fieldtype_id bigint,
	attribute_fieldtype_name character varying,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	entity_valid boolean,
	value_storage text[],
	regex_pattern text[],
	fieldtype_option_value text[],
	file_storage text[],
	display boolean,
	attribute_deletable boolean,
	entitytype_deletable boolean,
	entitytype_treeable boolean,
	result_count bigint
);


SET search_path = public, pg_catalog;

--
-- Name: att_value_save; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE att_value_save AS (
	id bigint,
	entity_valid boolean
);


--
-- Name: audit_summary_result; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE audit_summary_result AS (
	audit_id bigint,
	date timestamp with time zone,
	action character varying,
	entity_type_id bigint,
	entity bigint,
	table_name character varying,
	user_name character varying,
	count bigint
);


--
-- Name: box_entity_load; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE box_entity_load AS (
	box_id bigint,
	attribute_value_id bigint,
	attribute_id bigint,
	entity_id bigint,
	value_varchar character varying(1024),
	value_long bigint,
	value_date date,
	value_text text,
	mod_user character varying,
	search_index tsvector
);


--
-- Name: breakpoint; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE breakpoint AS (
	func oid,
	linenumber integer,
	targetname text
);


--
-- Name: complete_entity_box_count; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE complete_entity_box_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_fieldtype_id bigint,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	value_storage text[],
	regex_pattern text[],
	search_count bigint,
	box_id bigint
);


--
-- Name: complete_entity_box_id_count; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE complete_entity_box_id_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	template text,
	edit_template text,
	lock_mode boolean,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_datatype_name character varying,
	attribute_fieldtype_id bigint,
	attribute_fieldtype_name character varying,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	entity_valid boolean,
	value_storage text[],
	regex_pattern text[],
	fieldtype_option_value text[],
	file_storage text[],
	display boolean,
	attribute_deletable boolean,
	entitytype_deletable boolean,
	entitytype_treeable boolean,
	result_count bigint,
	box_id bigint
);


--
-- Name: complete_entity_search_count; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE complete_entity_search_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	template text,
	edit_template text,
	lock_mode boolean,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_datatype_name character varying,
	attribute_fieldtype_id bigint,
	attribute_fieldtype_name character varying,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	entity_valid boolean,
	value_storage text[],
	regex_pattern text[],
	fieldtype_option_value text[],
	file_storage text[],
	display boolean,
	attribute_deletable boolean,
	entitytype_deletable boolean,
	entitytype_treeable boolean,
	result_count bigint
);


--
-- Name: complete_entity_search_result_count; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE complete_entity_search_result_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_fieldtype_id bigint,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	result_count bigint,
	value_storage text[],
	regex_pattern text[]
);


--
-- Name: complex_entity_search_type; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE complex_entity_search_type AS (
	entity_type_ids bigint[],
	ct1_sdata character varying,
	ct1_attids bigint[],
	ct2_sdata character varying[],
	ct2_attids bigint[],
	ct2_oprts character varying[],
	ct3_sdata character varying[],
	ct3_attids bigint[],
	ct3_oprts character varying[],
	ct4_sdata character varying,
	ct4_attdata character varying,
	gn_sdata character varying
);


--
-- Name: fieldtype_options; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE fieldtype_options AS (
	fieldtype_id bigint,
	fieldtype_name character varying(125),
	display_order integer,
	display_label character varying(125),
	option character varying[]
);


--
-- Name: frame; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE frame AS (
	level integer,
	targetname text,
	func oid,
	linenumber integer,
	args text
);


--
-- Name: import_status; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE import_status AS (
	ent_ids text,
	num_attempted bigint,
	num_failed bigint
);


--
-- Name: omplete_entity_box_count; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE omplete_entity_box_count AS (
	entity_type_name character varying(125),
	entity_type_id bigint,
	attribute_id bigint,
	"order" integer,
	attribute_name character varying(255),
	attribute_datatype_id bigint,
	attribute_fieldtype_id bigint,
	indexed boolean,
	required boolean,
	mod_user character varying,
	entity_id bigint,
	value_storage text[],
	regex_pattern text[],
	search_count bigint,
	box_id bigint
);


--
-- Name: proxyinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE proxyinfo AS (
	serverversionstr text,
	serverversionnum integer,
	proxyapiver integer,
	serverprocessid integer
);


--
-- Name: targetinfo; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE targetinfo AS (
	target oid,
	schema oid,
	nargs integer,
	argtypes oidvector,
	targetname name,
	argmodes "char"[],
	argnames text[],
	targetlang oid,
	fqname text,
	returnsset boolean,
	returntype oid
);


--
-- Name: test; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE test AS (
	f1 bigint,
	f2 text
);


--
-- Name: var; Type: TYPE; Schema: public; Owner: -
--

CREATE TYPE var AS (
	name text,
	varclass character(1),
	linenumber integer,
	isunique boolean,
	isconst boolean,
	isnotnull boolean,
	dtype oid,
	value text
);


SET search_path = client_template, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: attribute_value_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_value_base (
    attribute_value_id bigint NOT NULL,
    attribute_id bigint NOT NULL,
    entity_id bigint NOT NULL
);


--
-- Name: attribute_value_base_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_value_base_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_value_base_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE attribute_value_base_id_seq OWNED BY attribute_value_base.attribute_value_id;


--
-- Name: attribute_value_base_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_value_base_id_seq', 36026252, true);


--
-- Name: attribute_value_storage_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_value_storage_base (
    value_varchar character varying(1024),
    value_long bigint,
    value_date date,
    value_time time without time zone,
    value_text text,
    mod_user character varying NOT NULL,
    search_index tsvector
)
INHERITS (attribute_value_base);


--
-- Name: all_roles_load(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION all_roles_load() RETURNS SETOF attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
    role_values client_template.attribute_value_storage_base;
    
BEGIN    
    FOR role_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base att_val, ONLY client_template.entity_base ent where att_val.attribute_id=130 and att_val.entity_id = ent.entity_id and ent.entity_type_id=40  LOOP
    IF (role_values.value_varchar LIKE '%ROLE_ADMINISTRATOR%') THEN
    ELSE
        RETURN NEXT role_values;
    END IF;    
        END LOOP;
    
      
 END;$$;


--
-- Name: FUNCTION all_roles_load(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION all_roles_load() IS 'Input : None

Output : attribute_value_storage_base   Table

Functionality : This is a  function to  load all the  ''Roles''  that we support for permissions.';


--
-- Name: assigned_roles_load(character varying[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION assigned_roles_load(authorities character varying[]) RETURNS SETOF attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
	ent_id bigint;
	values client_template.attribute_value_storage_base;
	
	
BEGIN	
	FOR i IN COALESCE(array_lower(authorities,1),0) .. COALESCE(array_upper(authorities,1),-1) LOOP
            SELECT INTO ent_id entity_id FROM only client_template.attribute_value_storage_base where value_varchar=authorities[i];
	    FOR values IN SELECT * FROM ONLY client_template.attribute_value_storage_base where entity_id=ent_id  LOOP
		IF (values.value_varchar = 'yes' and  values.value_varchar NOT LIKE '%ROLE_ADMINISTRATOR%' )THEN
		  RETURN QUERY SELECT *  FROM ONLY client_template.attribute_value_storage_base where entity_id = values.entity_id and attribute_id= 130  ;
		END IF;  
            END LOOP;
        END LOOP; 
        
 END;$$;


--
-- Name: FUNCTION assigned_roles_load(authorities character varying[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION assigned_roles_load(authorities character varying[]) IS 'Input : Array of authorities(eg: ''manager'',''user'',''supervisor''...etc).
Return Type: attribute_value_storage_base Tbale.
Functionality : This procedure returns all the roles assigned to the authorities except the authority ''ROLE_ADMIN''';


--
-- Name: attribute_datatype_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_datatype_base (
    datatype_id bigint NOT NULL,
    datatype_name character varying DEFAULT 125 NOT NULL
);


--
-- Name: TABLE attribute_datatype_base; Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON TABLE attribute_datatype_base IS 'This table holds list of of data types we support in our DB';


--
-- Name: attribute_datatypes_load(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_datatypes_load() RETURNS SETOF attribute_datatype_base
    LANGUAGE sql
    AS $$SELECT * FROM client_template.attribute_datatype_base order by datatype_id$$;


--
-- Name: FUNCTION attribute_datatypes_load(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_datatypes_load() IS 'Input : NONE

Returns : The data from the table  attribute_datatype_base.

Functionality : This function is used to load all the datatypes from attribute_datatype_base table.We use this load funtion on the attribute create form.';


--
-- Name: attribute_fieldtype_option_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_fieldtype_option_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_fieldtype_option_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_fieldtype_option_seq', 2, true);


--
-- Name: attribute_fieldtype_option_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_fieldtype_option_base (
    fieldtype_option_id bigint DEFAULT nextval('attribute_fieldtype_option_seq'::regclass) NOT NULL,
    attribute_fieldtype_id bigint NOT NULL,
    option character varying(1024)
);


--
-- Name: TABLE attribute_fieldtype_option_base; Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON TABLE attribute_fieldtype_option_base IS 'This table holds list of option for the filed types.';


--
-- Name: attribute_fieldtype_options_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_fieldtype_options_load(fieldtype_id bigint) RETURNS SETOF attribute_fieldtype_option_base
    LANGUAGE plpgsql
    AS $$BEGIN
RETURN QUERY SELECT * FROM ONLY client_template.attribute_fieldtype_option_base WHERE attribute_fieldtype_id = fieldtype_id;
END;$$;


--
-- Name: FUNCTION attribute_fieldtype_options_load(fieldtype_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_fieldtype_options_load(fieldtype_id bigint) IS 'Input : fieldtype_id 

Returns : The data from the table attribute_fieldtype_option_base.

Functionality : This function is used to load all the options that are associated with selected fieldtype from attribute_fieldtype_option_base table.We use this load funtion on the attribute create form.';


--
-- Name: attribute_fieldtypes_load(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_fieldtypes_load() RETURNS SETOF public.fieldtype_options
    LANGUAGE plpgsql
    AS $$DECLARE
  fld_bs client_template.attribute_fieldtype_base;
  fld_op_bs client_template.attribute_fieldtype_option_base;
  fld_opt_rtn public.fieldtype_options;
  opt character varying;
  opts character varying[];
BEGIN
  SELECT * INTO fld_bs FROM ONLY client_template.attribute_fieldtype_base order by display_order;
  FOR fld_bs IN SELECT * FROM ONLY client_template.attribute_fieldtype_base order by display_order LOOP
      FOR opt IN SELECT option FROM ONLY client_template.attribute_fieldtype_option_base WHERE attribute_fieldtype_id = fld_bs.fieldtype_id LOOP 
          opts := array_append(opts, opt);
      END LOOP;    
      fld_opt_rtn.fieldtype_id := fld_bs.fieldtype_id; 
      fld_opt_rtn.fieldtype_name := fld_bs.fieldtype_name;
      fld_opt_rtn.display_order := fld_bs.display_order;
      fld_opt_rtn.display_label := fld_bs.display_label;
      fld_opt_rtn.option[1] := opts; 
      RETURN NEXT fld_opt_rtn;
  END LOOP;
END;$$;


--
-- Name: FUNCTION attribute_fieldtypes_load(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_fieldtypes_load() IS 'Input : None
Return Type : Custom type fieldtype_options(combination of tabled attrubte_fieldtype_base &  attrubte_fieldtype_option_base).
Functionality : This function peull all the filed types along with options available for them.';


--
-- Name: attribute_value_file_storage_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_value_file_storage_seq
    START WITH 100
    INCREMENT BY 1
    MINVALUE 100
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_value_file_storage_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_value_file_storage_seq', 361, true);


--
-- Name: attribute_value_file_storage_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_value_file_storage_base (
    attribute_value_file_storage_id bigint DEFAULT nextval('attribute_value_file_storage_seq'::regclass) NOT NULL,
    attribute_id bigint NOT NULL,
    entity_id bigint NOT NULL,
    name character varying NOT NULL,
    description text,
    sort bigint,
    mod_user character varying NOT NULL
);


--
-- Name: attribute_file_value_save(attribute_value_file_storage_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_file_value_save(att_file_values attribute_value_file_storage_base[]) RETURNS attribute_value_file_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
    ent_typ_id bigint;
    afvid bigint;
    att_file_value client_template.attribute_value_file_storage_base;
    ent_save client_template.entity_base;
    flg boolean;
BEGIN
      flg := FALSE;
      FOR i IN COALESCE(array_lower(att_file_values,1),0) .. COALESCE(array_upper(att_file_values,1),-1) LOOP
          att_file_value := att_file_values[i];
          IF att_file_value.mod_user IS NULL THEN
             RAISE EXCEPTION 'You cannot save without username';
          END IF;
          IF (att_file_value.entity_id IS NULL) OR (att_file_value.entity_id = 0) THEN
            IF flg IS FALSE THEN
              SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_file_value.attribute_id;
              IF NOT FOUND THEN
              ELSE
                  ent_save.entity_type_id = ent_typ_id;
                  ent_save.mod_user = att_file_value.mod_user;
                  ent_id := client_template.entity_save_return_entityid(ent_save);
              END IF;
            END IF;
          ELSE
              SELECT INTO ent_id entity_id FROM ONLY client_template.entity_base WHERE entity_id = att_file_value.entity_id;
              IF NOT FOUND THEN
                 RAISE EXCEPTION 'Entity Does Not Exist';
              ELSE
              END IF;
          END IF;
          att_file_value.entity_id = ent_id;
          IF (att_file_value.attribute_value_file_storage_id IS NULL) OR (att_file_value.attribute_value_file_storage_id = 0) THEN
           SELECT INTO att_file_value.attribute_value_file_storage_id nextval('client_' || 'template.attribute_value_file_storage_seq');
           INSERT INTO client_template.attribute_value_file_storage_base VALUES(att_file_value.*)
           RETURNING * INTO att_file_value;
      ELSE
           SELECT INTO afvid attribute_value_file_storage_id FROM ONLY client_template.attribute_value_file_storage_base WHERE attribute_value_file_storage_id = att_file_value.attribute_value_file_storage_id AND attribute_id = att_file_value.attribute_id AND entity_id = att_file_value.entity_id;
           IF NOT FOUND THEN
              RAISE EXCEPTION 'One or more attribute values do not exist to update';
           ELSE
              UPDATE client_template.attribute_value_file_storage_base SET attribute_id=att_file_value.attribute_id, entity_id=att_file_value.entity_id, "name"=att_file_value."name", description=att_file_value.description, sort=att_file_value.sort, mod_user = att_file_value.mod_user WHERE attribute_value_file_storage_id = att_file_value.attribute_value_file_storage_id;
           END IF;
      END IF;
      flg := TRUE;
      IF array_upper(att_file_values,1) = i THEN
          PERFORM client_template.build_entity_idx_col(ent_id);
      END IF;
      RETURN att_file_value; 
      END LOOP;
       
END;$$;





--
-- Name: entity_delete(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_delete(ent_id bigint, username character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$DECLARE
         eid bigint;
         entity_data client_template.entity_base; 
         ent_data client_template.entity_base; 
BEGIN
      IF username IS NULL THEN
            RAISE EXCEPTION 'You cannot delete entity type without a user name';
      END IF; 
      IF ent_id = 0 THEN
            RAISE EXCEPTION 'You must pass in entity type to delete';
      END IF;
      SELECT * INTO ent_data FROM ONLY client_template.entity_base WHERE entity_id = ent_id;      
      SELECT * INTO entity_data FROM client_template.entity_base  WHERE entity_id = ent_id and mod_user = username;
      INSERT INTO client_template.audit_entity_base  VALUES (nextval('audit_' || 'base_audit_id_seq'), now(), username, 3,entity_data.entity_id, entity_data.entity_type_id );
      DELETE FROM ONLY client_template.entity_base WHERE entity_id = ent_id and mod_user = username;
END;$$;


--
-- Name: FUNCTION entity_delete(ent_id bigint, username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_delete(ent_id bigint, username character varying) IS 'Input : entity type id , user name.
Return Type: void.
Functionality : This procedure takes the entity id and user name as input and delete the entity from the entity base table. Once we delete the entity we create a row in the audit entity base table.

Note: Once the entity is deleted all its data related with the entities in attribute_value_storage_base tables are deleted. 
';




--
-- Name: FUNCTION attribute_file_value_save(att_file_values attribute_value_file_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_file_value_save(att_file_values attribute_value_file_storage_base[]) IS 'Input : attribute_value_file_storage_base Table.
Returns : Entity id 
Functinality : This function stores the data related to the files on the attribute_value_file_storage_base table.Here we check if the entity is already created ,if entity is created already we will update the data related to the entity id or else we will create new entity in the entity base table and save the data in file storage table with newly created entity id.Finally we return the entity id that is newly created or updated.';

--
-- Name: attribute_single_file_value_save(attribute_value_file_storage_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_single_file_value_save(att_single_file_value attribute_value_file_storage_base) RETURNS attribute_value_file_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
    ent_typ_id bigint;
    afvid bigint;
    att_file_value client_template.attribute_value_file_storage_base;
    ent_save client_template.entity_base;
    flg boolean;
BEGIN
      flg := FALSE;      
          att_file_value := att_single_file_value;
          IF att_file_value.mod_user IS NULL THEN
             RAISE EXCEPTION 'You cannot save without username';
          END IF;
          IF (att_file_value.entity_id IS NULL) OR (att_file_value.entity_id = 0) THEN
            IF flg IS FALSE THEN
              SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_file_value.attribute_id;
              IF NOT FOUND THEN
              ELSE
                  ent_save.entity_type_id = ent_typ_id;
                  ent_save.mod_user = att_file_value.mod_user;
                  ent_id := client_template.entity_save_return_entityid(ent_save);
              END IF;
            END IF;
          ELSE
              SELECT INTO ent_id entity_id FROM ONLY client_template.entity_base WHERE entity_id = att_file_value.entity_id;
              IF NOT FOUND THEN
                 RAISE EXCEPTION 'Entity Does Not Exist';
              ELSE
              END IF;
          END IF;
          att_file_value.entity_id = ent_id;
          IF (att_file_value.attribute_value_file_storage_id IS NULL) OR (att_file_value.attribute_value_file_storage_id = 0) THEN
           SELECT INTO att_file_value.attribute_value_file_storage_id nextval('client_' || 'template.attribute_value_file_storage_seq');
           INSERT INTO client_template.attribute_value_file_storage_base VALUES(att_file_value.*)
           RETURNING * INTO att_file_value;
      ELSE
           SELECT INTO afvid attribute_value_file_storage_id FROM ONLY client_template.attribute_value_file_storage_base WHERE attribute_value_file_storage_id = att_file_value.attribute_value_file_storage_id AND attribute_id = att_file_value.attribute_id AND entity_id = att_file_value.entity_id;
           IF NOT FOUND THEN
              RAISE EXCEPTION 'One or more attribute values do not exist to update';
           ELSE
              UPDATE client_template.attribute_value_file_storage_base SET attribute_id=att_file_value.attribute_id, entity_id=att_file_value.entity_id, "name"=att_file_value."name", description=att_file_value.description, sort=att_file_value.sort, mod_user = att_file_value.mod_user WHERE attribute_value_file_storage_id = att_file_value.attribute_value_file_storage_id;
           END IF;
      END IF;
      flg := TRUE;      
         PERFORM client_template.build_entity_idx_col(ent_id);     
      RETURN att_file_value;      
       
END;$$;


--
-- Name: FUNCTION attribute_single_file_value_save(att_single_file_value attribute_value_file_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_single_file_value_save(att_single_file_value attribute_value_file_storage_base[]) IS 'Input : attribute_value_file_storage_base Table.
Returns : Entity id 
Functinality : This function stores the data related to the files on the attribute_value_file_storage_base table.Here we check if the entity is already created ,if entity is created already we will update the data related to the entity id or else we will create new entity in the entity base table and save the data in file storage table with newly created entity id.Finally we return the entity id that is newly created or updated.';


--
-- Name: entity_attribute_base_entity_attribute_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE entity_attribute_base_entity_attribute_id_seq
    START WITH 130
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: entity_attribute_base_entity_attribute_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('entity_attribute_base_entity_attribute_id_seq', 9594, true);


--
-- Name: attribute_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_base (
    attribute_id bigint DEFAULT nextval('entity_attribute_base_entity_attribute_id_seq'::regclass) NOT NULL,
    "order" integer NOT NULL,
    entity_type_id bigint NOT NULL,
    attribute_name character varying(255) NOT NULL,
    attribute_datatype_id bigint NOT NULL,
    indexed boolean DEFAULT true NOT NULL,
    attribute_fieldtype_id bigint NOT NULL,
    required boolean,
    mod_user character varying NOT NULL,
    regex_id bigint,
    display boolean DEFAULT true NOT NULL,
    deletable boolean DEFAULT true NOT NULL
);


--
-- Name: attribute_fieldtype_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_fieldtype_base (
    fieldtype_id bigint NOT NULL,
    fieldtype_name character varying DEFAULT 125 NOT NULL,
    display_order integer,
    display_label character varying(125)
);


--
-- Name: TABLE attribute_fieldtype_base; Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON TABLE attribute_fieldtype_base IS 'This table holds list of of filed types  we support in our DB';


--
-- Name: attribute_fieldtype_option_value_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_fieldtype_option_value_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_fieldtype_option_value_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_fieldtype_option_value_seq', 1212, true);


--
-- Name: attribute_fieldtype_option_value_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE attribute_fieldtype_option_value_base (
    option_value_id bigint DEFAULT nextval('attribute_fieldtype_option_value_seq'::regclass) NOT NULL,
    attribute_id bigint NOT NULL,
    attribute_fieldtype_option_id bigint NOT NULL,
    value character varying(1024)
);


--
-- Name: TABLE attribute_fieldtype_option_value_base; Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON TABLE attribute_fieldtype_option_value_base IS 'This tables holds the option values of the filed types.';


--
-- Name: choice_attribute_detail_seq_id; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE choice_attribute_detail_seq_id
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: choice_attribute_detail_seq_id; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('choice_attribute_detail_seq_id', 709, true);


--
-- Name: choice_attribute_detail_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE choice_attribute_detail_base (
    choice_attribute_id bigint DEFAULT nextval('choice_attribute_detail_seq_id'::regclass) NOT NULL,
    attribute_id bigint NOT NULL,
    display_type character varying NOT NULL,
    choice_entity_type_attribute_id bigint NOT NULL,
    mod_user character varying NOT NULL
);


--
-- Name: entity_type_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE entity_type_base (
    entity_type_id bigint NOT NULL,
    entity_type_name character varying(125) NOT NULL,
    mod_user character varying NOT NULL,
    template text,
    edit_template text,
    lock_mode boolean DEFAULT false,
    deletable boolean DEFAULT true NOT NULL,
    treeable boolean DEFAULT false
);


--
-- Name: regex_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE regex_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: regex_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('regex_id_seq', 363, true);


--
-- Name: regex_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE regex_base (
    regex_id bigint DEFAULT nextval('regex_id_seq'::regclass) NOT NULL,
    pattern character varying(255),
    display_name character varying(255),
    custom boolean
);


--
-- Name: related_entity_type_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE related_entity_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: related_entity_type_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('related_entity_type_id_seq', 853, true);


--
-- Name: related_entity_type_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE related_entity_type_base (
    related_entity_type_id bigint DEFAULT nextval('related_entity_type_id_seq'::regclass) NOT NULL,
    attribute_id bigint NOT NULL,
    child_entity_type_id bigint NOT NULL,
    collapse boolean DEFAULT false,
    mod_user character varying NOT NULL
);


--
-- Name: complete_entity_type_base; Type: VIEW; Schema: client_template; Owner: -
--

CREATE VIEW complete_entity_type_base AS
    SELECT DISTINCT ent.entity_type_name, ent.template, ent.edit_template, ent.lock_mode, att.attribute_id, att."order", ent.entity_type_id, att.attribute_name, att.attribute_datatype_id, (SELECT dtb.datatype_name FROM ONLY attribute_datatype_base dtb WHERE (dtb.datatype_id = att.attribute_datatype_id)) AS attribute_datatype_name, att.indexed, att.attribute_fieldtype_id, (SELECT flt.fieldtype_name FROM ONLY attribute_fieldtype_base flt WHERE (flt.fieldtype_id = att.attribute_fieldtype_id)) AS attribute_fieldtype_name, att.required, att.mod_user, (SELECT ARRAY(SELECT textin(record_out(ROW(ret1.related_entity_type_id, ret1.attribute_id, ret1.child_entity_type_id, ret1.collapse))) AS textin FROM (ONLY related_entity_type_base ret1 LEFT JOIN ONLY entity_type_base ent1 ON ((ent1.entity_type_id = ret1.child_entity_type_id))) WHERE (ret1.attribute_id = att.attribute_id))) AS related_entity, (SELECT ARRAY(SELECT textin(record_out(ROW(ch.choice_attribute_id, ch.attribute_id, ch.display_type, ch.choice_entity_type_attribute_id))) AS textin FROM ONLY choice_attribute_detail_base ch WHERE (ch.attribute_id = att.attribute_id))) AS choice_attribute, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(reg.regex_id, reg.pattern, reg.display_name, reg.custom))) AS textin FROM ONLY regex_base reg WHERE (reg.regex_id = att.regex_id))) AS regex_pattern, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(afo.fieldtype_option_id, afo.attribute_fieldtype_id, afo.option, afv.option_value_id, afv.attribute_id, afv.value))) AS textin FROM ((ONLY attribute_fieldtype_option_base afo JOIN ONLY attribute_fieldtype_base af ON ((af.fieldtype_id = afo.attribute_fieldtype_id))) LEFT JOIN ONLY attribute_fieldtype_option_value_base afv ON ((afv.attribute_fieldtype_option_id = afo.fieldtype_option_id))) WHERE ((att.attribute_fieldtype_id = af.fieldtype_id) AND (att.attribute_id = afv.attribute_id)))) AS fieldtype_option_value, att.display, att.deletable AS attribute_deletable, ent.deletable AS entitytype_deletable, ent.treeable AS entitytype_treeable  FROM (ONLY entity_type_base ent LEFT JOIN ONLY attribute_base att ON ((att.entity_type_id = ent.entity_type_id))) ORDER BY ent.entity_type_id, att."order";


--
-- Name: attribute_reorder(bigint[], character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_reorder(attribute_ids bigint[], username character varying) RETURNS SETOF complete_entity_type_base
    LANGUAGE plpgsql
    AS $$
DECLARE
    eid bigint;   
    other_att client_template.attribute_base; 
    counter integer;
    complete_type client_template.complete_entity_type_base;
BEGIN
    SELECT INTO eid entity_type_id from ONLY client_template.attribute_base where attribute_id = attribute_ids[1];    
    FOR i IN COALESCE(array_lower(attribute_ids,1),0) .. COALESCE(array_upper(attribute_ids,1),-1) LOOP
    counter:=i;    
        UPDATE client_template.attribute_base set "order" = i WHERE attribute_id = attribute_ids[i] and entity_type_id = eid;        
    END LOOP;
    FOR other_att IN SELECT * from ONLY client_template.attribute_base where entity_type_id = eid and attribute_id <> ALL(attribute_ids) LOOP	
        counter:=counter+1;
        UPDATE client_template.attribute_base set "order" = counter WHERE attribute_id = other_att.attribute_id and entity_type_id = eid;
     END LOOP;
     FOR complete_type IN SELECT * FROM ONLY client_template.complete_entity_type_base WHERE entity_type_id = eid LOOP
        RETURN NEXT complete_type;
    END LOOP;
END;
$$;


--
-- Name: FUNCTION attribute_reorder(attribute_ids bigint[], username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_reorder(attribute_ids bigint[], username character varying) IS 'Input : Array of attribute ids and User name.
Return Type: Complete_entity_type_base View.
Functionality:This procedure to ensure proper oder is maintained in the attributes of an entity type.While adding a new attribute or updating an existing attribute depending on the oredr set to the attribute the remaining attributes are sorted and adjusted.Golden rule is no two attributes of same entity type should not have same order ';


--
-- Name: attribute_to_delete(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_to_delete(attribute_to_delete bigint, username character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$DECLARE
      eid bigint;
      att_value_bs client_template.attribute_value_storage_base;
      att_bs client_template.attribute_base;
      entity_data  client_template.entity_base ;
      choice_data client_template.choice_attribute_detail_base ;
      related_data client_template.related_entity_type_base ;  
      att_ft_op client_template.attribute_fieldtype_option_value_base;               
BEGIN
      IF username IS NULL THEN
            RAISE EXCEPTION 'You must pass in user name';
      END IF; 
      IF attribute_to_delete = 0 THEN
            RAISE EXCEPTION 'You must pass in attribute id to delete';
      END IF;
     
      SELECT * INTO att_bs  FROM ONLY client_template.attribute_base WHERE attribute_id = attribute_to_delete  and mod_user = username ; 
      IF NOT FOUND THEN
            RAISE EXCEPTION 'No data to delete';
      ELSE  
         IF att_bs.deletable IS TRUE THEN
            --SELECT * INTO att_ft_op FROM ONLY client_template.attribute_fieldtype_option_base WHERE attribute_fieldtype_id = att_bs.attribute_fieldtype_id;
            FOR att_ft_op IN SELECT * FROM ONLY client_template.attribute_fieldtype_option_value_base WHERE attribute_id = att_bs.attribute_id LOOP
                DELETE FROM ONLY client_template.attribute_fieldtype_option_value_base WHERE  attribute_id = attribute_to_delete;
            END LOOP;
            DELETE FROM ONLY client_template.attribute_base WHERE attribute_id =  attribute_to_delete  and mod_user =  username;
            INSERT INTO client_template.audit_attribute_base VALUES (nextval('audit_' || 'base_audit_id_seq'), now(), username, 3, att_bs.attribute_id,att_bs."order",att_bs.entity_type_id, att_bs.attribute_name, 
            att_bs.attribute_datatype_id, att_bs.indexed, att_bs.attribute_fieldtype_id, att_bs.required, att_bs.regex_id);
         ELSE
            RAISE EXCEPTION 'Attribute Not Deletable';
         END IF;     
      END IF;
END;$$;


--
-- Name: FUNCTION attribute_to_delete(attribute_to_delete bigint, username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_to_delete(attribute_to_delete bigint, username character varying) IS 'Input : Attribute id & User name
Return Type : Void
Functionality : This procedure deletes an attribute from the attribute_base table.The procedure gets the attribute id from the user and checks in the attribute_base table if the attribute is deletable.If the attribute is deletable then it is deleted otherwise an error msg is passed to user that the attribute is not deletable.';


--
-- Name: attribute_value_save(attribute_value_storage_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_value_save(att_value attribute_value_storage_base) RETURNS public.att_value_save
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
    ent_typ_id bigint;
    avid bigint;
    ent_att_value client_template.attribute_value_storage_base;
    ent_save client_template.entity_base;
    ent_valid public.att_value_save;
    att_fieldtype_id bigint;
    att_value_varchar varchar;
    avv varchar;
BEGIN
        IF att_value.mod_user IS NULL THEN
             RAISE EXCEPTION 'You cannot save without a mod username';
        END IF;
        IF (att_value.entity_id IS NULL) OR (att_value.entity_id = 0) THEN
           SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_value.attribute_id;
           IF NOT FOUND THEN
           ELSE
               ent_save.entity_type_id = ent_typ_id;
               ent_save.mod_user = att_value.mod_user;
               ent_id := client_template.entity_save_return_entityid(ent_save);
           END IF;
        ELSE
           SELECT INTO ent_id entity_id FROM ONLY client_template.entity_base WHERE entity_id = att_value.entity_id;
           IF NOT FOUND THEN
              RAISE EXCEPTION 'Entity Does Not Exist';
           ELSE
              SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_value.attribute_id;
           END IF; 
        END IF;  
        att_value_varchar = 'ROLE_'|| att_value.value_varchar;
        att_value_varchar = lower(att_value_varchar);
        IF (att_value.value_varchar IS NULL) AND (att_value.value_long IS NULL) AND (att_value.value_date IS NULL) AND (att_value.value_time IS NULL) AND (att_value.value_text IS NULL)  THEN
           RAISE EXCEPTION 'No Value To Save';
        ELSE
            att_value.entity_id = ent_id;
            SELECT INTO avid attribute_value_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = att_value.attribute_id AND entity_id = att_value.entity_id;
	    IF NOT FOUND THEN
	       SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.entity_base WHERE entity_id = att_value.entity_id;
	       IF ent_typ_id = 41 THEN
	           IF att_value.attribute_id = 130 THEN
	           		SELECT INTO avv value_varchar FROM ONLY testclient.attribute_value_storage_testclient WHERE attribute_id = 130 AND lower(value_varchar) = att_value_varchar;
			        IF NOT FOUND THEN
			        	att_value.value_varchar:= 'ROLE_'|| att_value.value_varchar;		         
			      	ELSE
				 		RAISE EXCEPTION 'Role Name Already Exists';
		      		END IF;
               END IF;
		   SELECT INTO att_value.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
		   INSERT INTO client_template.attribute_value_storage_base VALUES(att_value.*)
		   RETURNING * INTO att_value;
	       ELSE      
	          IF (client_template.entity_value_validation(att_value)) THEN
	            IF att_value.attribute_id = 130 THEN
	           		SELECT INTO avv value_varchar FROM ONLY testclient.attribute_value_storage_testclient WHERE attribute_id = 130 AND lower(value_varchar) = att_value_varchar;
			        IF NOT FOUND THEN
			        	att_value.value_varchar:= 'ROLE_'|| att_value.value_varchar;		         
			      	ELSE
				 		RAISE EXCEPTION 'Role Name Already Exists';
		      		END IF;
                END IF;
		    SELECT INTO att_value.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
		    INSERT INTO client_template.attribute_value_storage_base VALUES(att_value.*)
		    RETURNING * INTO att_value;
	          ELSE
	          END IF;
	        END IF;  
	    ELSE
		SELECT INTO avid attribute_value_id from client_template.attribute_value_storage_base WHERE attribute_id = att_value.attribute_id AND entity_id = att_value.entity_id;
		IF NOT FOUND THEN
            	   RAISE EXCEPTION 'One or more attribute values do not exist to update';
		ELSE
		  IF ent_typ_id = 41 THEN
	      		IF att_value.attribute_id = 130 THEN
	           	    SELECT INTO avv value_varchar FROM ONLY testclient.attribute_value_storage_testclient WHERE attribute_id = 130 AND lower(value_varchar) = att_value_varchar;
			        IF NOT FOUND THEN
			        	att_value.value_varchar:= 'ROLE_'|| att_value.value_varchar;		         
			      	ELSE
				 		RAISE EXCEPTION 'Role Name Already Exists';
		      		END IF;
                END IF;
                UPDATE client_template.attribute_value_storage_base SET attribute_value_id=avid, attribute_id=att_value.attribute_id, entity_id=att_value.entity_id, value_varchar=att_value.value_varchar,  value_long=att_value.value_long, value_date=att_value.value_date, value_text=att_value.value_text, mod_user=att_value.mod_user WHERE attribute_value_id=avid;
 
	          ELSE      
                      IF (client_template.entity_value_validation(att_value)) THEN
             	        IF att_value.attribute_id = 130 THEN
			           		SELECT INTO avv value_varchar FROM ONLY testclient.attribute_value_storage_testclient WHERE attribute_id = 130 AND lower(value_varchar) = att_value_varchar;
					        IF NOT FOUND THEN
					        	att_value.value_varchar:= 'ROLE_'|| att_value.value_varchar;		         
					      	ELSE
						 		RAISE EXCEPTION 'Role Name Already Exists';
				      		END IF;
		               END IF;
                         -- Insert multiple records into AVS only if fieldtype is either file/image
						SELECT INTO att_fieldtype_id attribute_fieldtype_id FROM ONLY testclient.attribute_testclient WHERE attribute_id = att_value.attribute_id;
						IF (att_fieldtype_id = 14) OR (att_fieldtype_id = 15)  THEN
							SELECT INTO att_value.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
							INSERT INTO testclient.attribute_value_storage_testclient VALUES(att_value.*)
							RETURNING * INTO att_value;
						ELSE 
							UPDATE testclient.attribute_value_storage_testclient SET attribute_value_id=avid, attribute_id=att_value.attribute_id, entity_id=att_value.entity_id, value_varchar=att_value.value_varchar,  value_long=att_value.value_long, value_date=att_value.value_date, value_time=att_value.value_time, value_text=att_value.value_text, mod_user=att_value.mod_user, attribute_value_file_storage_id=att_value.attribute_value_file_storage_id WHERE attribute_value_id=avid;
						END IF;
                      ELSE 
	              END IF;		
                  END IF;
		 END IF;
	    END IF;
        END IF;
        PERFORM client_template.build_entity_idx_col(ent_id);    
        SELECT INTO ent_valid entity_id,entity_valid  FROM ONLY client_template.entity_base WHERE entity_id = ent_id;    
        RETURN ent_valid;
END;$$;


--
-- Name: FUNCTION attribute_value_save(att_value attribute_value_storage_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_value_save(att_value attribute_value_storage_base) IS 'Input : Object of attribute_value_storage_base Table.
Return Type : Custom Type att_value_save [ entity_id & entity_valid]
Functionality : This procedure saves entities to the attribute_value_storage_base table.We check the entity id from the input parameter,if it is an existing entity then we update the row in the table with entity id and attribute id otherwise we will creat a new entity id from the entity base table and save the data to the table with new entity id and attribute id. ';


--
-- Name: attribute_values_save(attribute_value_storage_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_values_save(attribute_values_to_save attribute_value_storage_base[]) RETURNS SETOF attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
	attribute_value_to_save client_template.attribute_value_storage_base; 
	avid bigint;
BEGIN	
	FOR i IN COALESCE(array_lower(attribute_values_to_save,1),0) .. COALESCE(array_upper(attribute_values_to_save,1),-1) LOOP
		attribute_value_to_save:=attribute_values_to_save[i];
		IF attribute_value_to_save.mod_user IS NULL THEN
		       RAISE EXCEPTION 'You must pass user name';
	        END IF; 
		IF attribute_value_to_save.attribute_value_id IS NULL or attribute_value_to_save.attribute_value_id = 0 THEN
			SELECT INTO attribute_value_to_save.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
			INSERT INTO client_template.attribute_value_storage_base VALUES(attribute_value_to_save.*)
			RETURNING * INTO attribute_value_to_save;
		ELSE
			SELECT INTO avid attribute_value_id from client_template.attribute_value_storage_base WHERE attribute_value_id = attribute_value_to_save.attribute_value_id;
			IF NOT FOUND THEN
				RAISE EXCEPTION 'One or more attribute values do not exist to update';
			ELSE 
				UPDATE client_template.attribute_value_storage_base SET attribute_value_id=attribute_value_to_save.attribute_value_id, attribute_id=attribute_value_to_save.attribute_id, entity_id=attribute_value_to_save.entity_id, value_varchar=attribute_value_to_save.value_varchar,  value_long=attribute_value_to_save.value_long, value_date=attribute_value_to_save.value_date, value_text=attribute_value_to_save.value_text, mod_user=attribute_value_to_save.mod_user, search_index =attribute_value_to_save.search_index  WHERE attribute_value_id=attribute_value_to_save.attribute_value_id;
			END IF;
		END IF;
	END LOOP;
	FOR attribute_value_to_save IN SELECT * FROM ONLY client_template.attribute_value_storage_base LOOP
		RETURN NEXT attribute_value_to_save;
	END LOOP;
END;$$;


--
-- Name: FUNCTION attribute_values_save(attribute_values_to_save attribute_value_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_values_save(attribute_values_to_save attribute_value_storage_base[]) IS 'Input : Array of Objects of attribute_value_storage_base Table.
Return Type :  attribute_value_storage_base Table.
Functionality : This procedure saves entities to the attribute_value_storage_base table.We check the entity id from the input parameter,if it is an existing entity then we update the row in the table with entity id and attribute id otherwise we will creat a new entity id from the entity base table and save the data to the table with new entity id and attribute id.

Note: This function works similar to that of attribute_value_save() the only differnce between both of them is this  function can save multiple values of an entity where as attribute_value_save() saves only single value of an entity at a time.';


--
-- Name: attribute_values_save_returnvoid(attribute_value_storage_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION attribute_values_save_returnvoid(attribute_values_to_save attribute_value_storage_base[]) RETURNS void
    LANGUAGE plpgsql
    AS $$DECLARE
	attribute_value_to_save client_template.attribute_value_storage_base; 
	avid bigint;
BEGIN	
	FOR i IN COALESCE(array_lower(attribute_values_to_save,1),0) .. COALESCE(array_upper(attribute_values_to_save,1),-1) LOOP
		attribute_value_to_save:=attribute_values_to_save[i];
		IF attribute_value_to_save.mod_user IS NULL THEN
		       RAISE EXCEPTION 'You must pass user name';
	        END IF;
	        IF attribute_value_to_save.attribute_id = 130 THEN
                   attribute_value_to_save.value_varchar:= 'ROLE_'|| attribute_value_to_save.value_varchar;
                END IF;  
		--IF attribute_value_to_save.attribute_value_id IS NULL or attribute_value_to_save.attribute_value_id = 0 THEN
		SELECT INTO avid attribute_value_id from client_template.attribute_value_storage_base WHERE attribute_id = attribute_value_to_save.attribute_id AND entity_id = attribute_value_to_save.entity_id;
		IF NOT FOUND THEN
			SELECT INTO attribute_value_to_save.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
			INSERT INTO client_template.attribute_value_storage_base VALUES(attribute_value_to_save.*)
			RETURNING * INTO attribute_value_to_save;
		ELSE
			--SELECT INTO avid attribute_value_id from client_template.attribute_value_storage_base WHERE attribute_value_id = attribute_value_to_save.attribute_value_id;
			SELECT INTO avid attribute_value_id from client_template.attribute_value_storage_base WHERE attribute_id = attribute_value_to_save.attribute_id AND entity_id = attribute_value_to_save.entity_id;
			IF NOT FOUND THEN
				RAISE EXCEPTION 'One or more attribute values do not exist to update';
			ELSE 
				UPDATE client_template.attribute_value_storage_base SET attribute_value_id=attribute_value_to_save.attribute_value_id, attribute_id=attribute_value_to_save.attribute_id, entity_id=attribute_value_to_save.entity_id, value_varchar=attribute_value_to_save.value_varchar,  value_long=attribute_value_to_save.value_long, value_date=attribute_value_to_save.value_date, value_text=attribute_value_to_save.value_text, mod_user=attribute_value_to_save.mod_user, search_index =attribute_value_to_save.search_index  WHERE attribute_value_id=attribute_value_to_save.attribute_value_id;
			END IF;
		END IF;
	END LOOP;
END;$$;


--
-- Name: FUNCTION attribute_values_save_returnvoid(attribute_values_to_save attribute_value_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION attribute_values_save_returnvoid(attribute_values_to_save attribute_value_storage_base[]) IS 'Input : Array of Objects of attribute_value_storage_base Table.
Return Type :  Void.
Functionality : This procedure saves entities to the attribute_value_storage_base table.We check the entity id from the input parameter,if it is an existing entity then we update the row in the table with entity id and attribute id otherwise we will creat a new entity id from the entity base table and save the data to the table with new entity id and attribute id.

Note: This function works similar to that of attribute_values_save() the only differnce between both of them is this  function returns void.';


SET search_path = public, pg_catalog;

--
-- Name: audit_base; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE audit_base (
    audit_id bigint NOT NULL,
    date timestamp with time zone DEFAULT now() NOT NULL,
    mod_user character varying,
    audit_action integer
);


--
-- Name: audit_base_audit_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE audit_base_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: audit_base_audit_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE audit_base_audit_id_seq OWNED BY audit_base.audit_id;


--
-- Name: audit_base_audit_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('audit_base_audit_id_seq', 65233994, true);


--
-- Name: audit_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE audit_base ALTER COLUMN audit_id SET DEFAULT nextval('audit_base_audit_id_seq'::regclass);


SET search_path = client_template, pg_catalog;

--
-- Name: audit_attribute_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_attribute_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, attribute_base);


--
-- Name: audit_attribute(bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_attribute(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_attribute_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_base WHERE attribute_id = att_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_base WHERE attribute_id = att_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_attribute(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_attribute(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: attribute_id ,start date, end date, limit and offset.
RETURN TYPE:audit_attribute_base table.
FUNCTIONALITY:This procedure load all the records from the audit attribute table with in the date range .If  date range is not given then it returns all the audit records.';


--
-- Name: audit_attribute_value_file_storage_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_attribute_value_file_storage_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, attribute_value_file_storage_base);


--
-- Name: audit_attribute_value_file_storage(bigint, bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_attribute_value_file_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_attribute_value_file_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_value_file_storage_base WHERE att_id = attribute_id AND entity_id = ent_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_value_file_storage_base WHERE att_id = attribute_id AND entity_id = ent_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_attribute_value_file_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_attribute_value_file_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: attribute_id , entity_id, start date, end date, limit and offset.
RETURN TYPE:audit_attribute_value_file_storage_base.
FUNCTIONALITY:This procedure load all the records from the audit_attribute_value_file_storage_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: audit_attribute_value_storage_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_attribute_value_storage_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, attribute_value_storage_base);


--
-- Name: audit_attribute_value_storage(bigint, bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_attribute_value_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_value_storage_base WHERE att_id = attribute_id AND entity_id = ent_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_attribute_value_storage_base WHERE att_id = attribute_id AND entity_id = ent_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_attribute_value_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_attribute_value_storage(att_id bigint, ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: attribute_id , entity_id, start date, end date, limit and offset.
RETURN TYPE:audit_attribute_value_storage_base.
FUNCTIONALITY:This procedure load all the records from the audit_attribute_value_storage_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: audit_choice_attribute_detail_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_choice_attribute_detail_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, choice_attribute_detail_base);


--
-- Name: audit_choice_attribute_detail(bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_choice_attribute_detail(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_choice_attribute_detail_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_choice_attribute_detail_base WHERE att_id = attribute_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_choice_attribute_detail_base WHERE att_id = attribute_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_choice_attribute_detail(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_choice_attribute_detail(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: attribute_id , start date, end date, limit and offset.
RETURN TYPE: audit_choice_attribute_detail_base Table.
FUNCTIONALITY:This procedure load all the records from the audit_choice_attribute_detail_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: entity_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE entity_base (
    entity_id bigint NOT NULL,
    entity_type_id bigint NOT NULL,
    mod_user character varying NOT NULL,
    search_index tsvector,
    entity_valid boolean DEFAULT false
);


--
-- Name: entity_entity_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE entity_entity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: entity_entity_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE entity_entity_id_seq OWNED BY entity_base.entity_id;


--
-- Name: entity_entity_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('entity_entity_id_seq', 7224551, true);


--
-- Name: audit_entity_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_entity_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, entity_base);


--
-- Name: audit_entity(bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_entity(ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_entity_base WHERE entity_id = ent_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_entity_base WHERE entity_id = ent_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_entity(ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_entity(ent_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: entity_id , start date, end date, limit and offset.
RETURN TYPE: audit_entity_base Table.
FUNCTIONALITY:This procedure load all the records from the audit_entity_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';



--
-- Name: audit_entity_md(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_entity_md(ent_id bigint) RETURNS timestamp
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
      SELECT MAX("date") AS mod_date FROM ONLY client_template.audit_entity_base WHERE entity_id = ent_id;
END;
$$;


--
-- Name: FUNCTION audit_entity_md(ent_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_entity_md(ent_id bigint) IS 'INPUT: entity_id.
RETURN TYPE: timestamp.
FUNCTIONALITY:This procedure load all the records from the audit_entity_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: entity_audit; Type: VIEW; Schema: client_template; Owner: -
--

CREATE VIEW entity_audit AS
    SELECT DISTINCT et.entity_id, et.mod_user, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(aet.audit_id, aet.date, aet.mod_user, aet.audit_action, aet.entity_id, aet.entity_type_id, aet.search_index, aet.entity_valid))) AS textin FROM ONLY audit_entity_base aet WHERE (et.entity_id = aet.entity_id))) AS audit_entity, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(attval.audit_id, attval.date, attval.mod_user, attval.audit_action, attval.attribute_value_id, attval.attribute_id, attval.entity_id, attval.value_varchar, attval.value_long, attval.value_date, attval.value_time, attval.value_text, attval.mod_user, attval.search_index))) AS textin FROM ONLY audit_attribute_value_storage_base attval WHERE (et.entity_id = attval.entity_id))) AS audit_value_storage, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(flval.audit_id, flval.date, flval.mod_user, flval.audit_action, flval.attribute_value_file_storage_id, flval.attribute_id, flval.entity_id, flval.name, flval.description, flval.sort, flval.mod_user))) AS textin FROM (ONLY audit_attribute_value_file_storage_base flval JOIN ONLY entity_base et1 ON ((flval.entity_id = et1.entity_id))) WHERE (et.entity_id = flval.entity_id))) AS audit_file_storage FROM ONLY audit_entity_base et;


--
-- Name: audit_entity_detail(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_entity_detail(ent_id bigint, usr_name character varying) RETURNS SETOF entity_audit
    LANGUAGE plpgsql
    AS $$
DECLARE 
BEGIN
       IF ent_id IS NULL AND usr_name IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_audit;
          --RAISE EXCEPTION 'You must pass either an entity_id or user name';
       END IF;
       IF ent_id IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_audit WHERE mod_user = usr_name; 
       ELSIF usr_name IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_audit WHERE entity_id = ent_id; 
       ELSE 
          RETURN QUERY SELECT * FROM ONLY client_template.entity_audit WHERE mod_user = usr_name AND entity_id = ent_id; 
       END IF;     
         
END;$$;


--
-- Name: FUNCTION audit_entity_detail(ent_id bigint, usr_name character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_entity_detail(ent_id bigint, usr_name character varying) IS 'INPUT : entity_id & user name
RETURN TYPE : audit_entity_detail (VIEW)
FUNCTIONALITY:  This procedure pull the records from the audit_entity_detail view  base on the criteria given by the user.If no criteria is given ie if null is passed for two input parameters then the whole audit detail view is returned.

';


--
-- Name: entity_type_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE entity_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: entity_type_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE entity_type_id_seq OWNED BY entity_type_base.entity_type_id;


--
-- Name: entity_type_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('entity_type_id_seq', 6539, true);


--
-- Name: audit_entity_type_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_entity_type_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, entity_type_base);


--
-- Name: audit_entity_type(bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_entity_type(ent_typ_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_entity_type_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_entity_type_base WHERE entity_type_id = ent_typ_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_entity_type_base WHERE entity_type_id = ent_typ_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_entity_type(ent_typ_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_entity_type(ent_typ_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: entity_type_id , start date, end date, limit and offset.
RETURN TYPE: audit_entity_type_base Table.
FUNCTIONALITY:This procedure load all the records from the audit_entity_type_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: audit_related_entity_type_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_related_entity_type_base (
    audit_id bigint DEFAULT nextval('public.audit_base_audit_id_seq'::regclass)
)
INHERITS (public.audit_base, related_entity_type_base);


--
-- Name: entity_type_audit; Type: VIEW; Schema: client_template; Owner: -
--

CREATE VIEW entity_type_audit AS
    SELECT DISTINCT etyp.entity_type_id, etyp.mod_user, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(aetyp.audit_id, aetyp.date, aetyp.mod_user, aetyp.audit_action, aetyp.entity_type_id, aetyp.entity_type_name, aetyp.template, aetyp.edit_template, aetyp.lock_mode, aetyp.deletable, aetyp.treeable))) AS textin FROM ONLY audit_entity_type_base aetyp WHERE (etyp.entity_type_id = aetyp.entity_type_id))) AS audit_entity_type, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(att.audit_id, att.date, att.mod_user, att.audit_action, att.attribute_id, att."order", att.entity_type_id, att.attribute_name, att.attribute_datatype_id, att.indexed, att.attribute_fieldtype_id, att.required, att.regex_id, att.display, att.deletable))) AS textin FROM ONLY audit_attribute_base att WHERE (etyp.entity_type_id = att.entity_type_id))) AS audit_attribute, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(ch.audit_id, ch.date, ch.mod_user, ch.audit_action, ch.choice_attribute_id, ch.attribute_id, ch.display_type, ch.choice_entity_type_attribute_id))) AS textin FROM (ONLY audit_choice_attribute_detail_base ch JOIN ONLY audit_attribute_base cat ON ((cat.attribute_id = ch.attribute_id))) WHERE (etyp.entity_type_id = cat.entity_type_id))) AS audit_choice, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(rel.audit_id, rel.date, rel.mod_user, rel.audit_action, rel.related_entity_type_id, rel.attribute_id, rel.child_entity_type_id, rel.collapse))) AS textin FROM (ONLY audit_related_entity_type_base rel JOIN ONLY audit_attribute_base rlt ON ((rlt.attribute_id = rel.attribute_id))) WHERE (etyp.entity_type_id = rlt.entity_type_id))) AS audit_related_entity FROM ONLY audit_entity_base etyp;


--
-- Name: audit_entity_type_detail(bigint, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_entity_type_detail(ent_typ_id bigint, usr_name character varying, lmt bigint, ofst bigint) RETURNS SETOF entity_type_audit
    LANGUAGE plpgsql
    AS $$
DECLARE 
result_data client_template.complete_entity_base;
BEGIN
       IF ent_typ_id IS NULL AND usr_name IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_type_audit ORDER BY entity_type_id ASC LIMIT lmt OFFSET ofst;
          --RAISE EXCEPTION 'You must pass either an entity_id or user name';
       END IF;
       IF ent_typ_id IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_type_audit WHERE mod_user = usr_name ORDER BY entity_type_id ASC LIMIT lmt OFFSET ofst; 
       ELSIF usr_name IS NULL THEN
          RETURN QUERY SELECT * FROM ONLY client_template.entity_type_audit WHERE entity_type_id = ent_typ_id ORDER BY entity_type_id ASC LIMIT lmt OFFSET ofst; 
       ELSE 
          RETURN QUERY SELECT * FROM ONLY client_template.entity_type_audit WHERE mod_user = usr_name AND entity_type_id = ent_typ_id ORDER BY entity_type_id ASC LIMIT lmt OFFSET ofst; 
       END IF;     
         
END;$$;


--
-- Name: audit_related_entity_type(bigint, date, date, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_related_entity_type(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) RETURNS SETOF audit_related_entity_type_base
    LANGUAGE plpgsql
    AS $$DECLARE
BEGIN
  IF (start_date IS NOT NULL AND end_date IS NOT NULL )THEN
      --Raise Exception 'data';
      RETURN QUERY SELECT * FROM ONLY client_template.audit_related_entity_type_base WHERE att_id = attribute_id AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst;
  ELSE
      RETURN QUERY SELECT * FROM ONLY client_template.audit_related_entity_type_base WHERE att_id = attribute_id ORDER BY date DESC LIMIT lmt OFFSET ofst;
  END IF;    
END;
$$;


--
-- Name: FUNCTION audit_related_entity_type(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION audit_related_entity_type(att_id bigint, start_date date, end_date date, lmt bigint, ofst bigint) IS 'INPUT: attribute_id , start date, end date, limit and offset.
RETURN TYPE: audit_related_entity_type_base Table.
FUNCTIONALITY:This procedure load all the records from the audit_related_entity_type_base table with in the date range given by the user .If  date range is not given then it returns all the audit records.';


--
-- Name: audit_summary_detail(date, date, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION audit_summary_detail(start_date date, end_date date, usr_name character varying, lmt bigint, ofst bigint) RETURNS SETOF public.audit_summary_result
    LANGUAGE plpgsql
    AS $$
DECLARE
rs_data public.audit_summary_result;
rs_cnt bigint;
BEGIN

  IF (start_date IS NULL)  AND (end_date IS NULL ) THEN
     IF usr_name IS NULL THEN
        SELECT COUNT(*) INTO rs_cnt FROM ONLY client_template.audit_summary_base;
        FOR rs_data IN SELECT * FROM ONLY client_template.audit_summary_base ORDER BY date DESC LIMIT lmt OFFSET ofst LOOP
            rs_data.count := rs_cnt;
            RETURN NEXT rs_data;
        END LOOP;
     ELSE
        SELECT COUNT(*) INTO rs_cnt FROM ONLY client_template.audit_summary_base WHERE user_name = usr_name;
        FOR rs_data IN SELECT * FROM ONLY client_template.audit_summary_base WHERE user_name = usr_name ORDER BY date DESC LIMIT lmt OFFSET ofst LOOP
            rs_data.count := rs_cnt;
            RETURN NEXT rs_data;
        END LOOP;
     END IF;   
  ELSE
     IF usr_name IS NULL THEN
        SELECT COUNT(*) INTO rs_cnt FROM ONLY client_template.audit_summary_base WHERE date BETWEEN start_date AND end_date;
        FOR rs_data IN SELECT * FROM ONLY client_template.audit_summary_base WHERE date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst LOOP
            rs_data.count := rs_cnt;
            RETURN NEXT rs_data;
        END LOOP;
     ELSE
        SELECT COUNT(*) INTO rs_cnt FROM ONLY client_template.audit_summary_base WHERE user_name = usr_name AND date BETWEEN start_date AND end_date;
        FOR rs_data IN SELECT * FROM ONLY client_template.audit_summary_base WHERE user_name = usr_name AND date BETWEEN start_date AND end_date ORDER BY date DESC LIMIT lmt OFFSET ofst LOOP
            rs_data.count := rs_cnt;
            RETURN NEXT rs_data;
        END LOOP;
     END IF;   
  END IF;   
        
END;$$;


--
-- Name: users; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE users (
    username character varying(50) NOT NULL,
    password character varying(50) NOT NULL,
    enabled boolean NOT NULL
);


--
-- Name: authenticate(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION authenticate(user_name character varying) RETURNS SETOF users
    LANGUAGE plpgsql
    AS $$
DECLARE
	users_rec client_template.users;
BEGIN
	SELECT INTO users_rec 
	(SELECT value_varchar 
		FROM ONLY client_template.attribute_value_storage_base sb 
		WHERE sb.attribute_id = 125 and value_varchar = user_name),
	(SELECT value_varchar 
		FROM ONLY client_template.attribute_value_storage_base sb
		WHERE sb.attribute_id = 128 and entity_id = (SELECT entity_id FROM ONLY client_template.attribute_value_storage_base sb WHERE sb.attribute_id = 125 and value_varchar = user_name)),
	true;
	return NEXT users_rec;
END;$$;


--
-- Name: FUNCTION authenticate(user_name character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION authenticate(user_name character varying) IS 'Input : user name
Return Type: users table
Functionality : This procedure is used to authenticate by checking the user name and the password combination with the same enetered by the user while logging into our application.';


--
-- Name: build_entity_idx_col(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION build_entity_idx_col(ent_id bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
     attribute client_template.attribute_base;
     attribute_value client_template.attribute_value_storage_base;
     ent_type bigint;
     text_result text;
     final_text tsvector;
     set_weight tsvector;
BEGIN
     final_text:='';
      FOR attribute_value in SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_id LOOP
            SELECT * INTO attribute FROM ONLY client_template.attribute_base where attribute_id = attribute_value.attribute_id and attribute_value.entity_id = ent_id;
            IF attribute.indexed = TRUE THEN          
                IF attribute.attribute_datatype_id = 1 THEN
                   text_result:=attribute_value.value_varchar ;
                ELSIF attribute.attribute_datatype_id = 2 THEN
                    text_result:=attribute_value.value_long ;
                ELSIF attribute.attribute_datatype_id = 3 THEN
                    text_result:=attribute_value.value_date ;
                ELSIF attribute.attribute_datatype_id = 4 THEN
                    text_result:=attribute_value.value_text ;
                ELSIF attribute.attribute_datatype_id = 5 THEN
                    text_result:=attribute_value.value_time ;
                ELSE
                     RAISE EXCEPTION 'Did not find the type';
                 END IF;                   
            END IF;
          
                IF attribute.order=1 THEN
                    set_weight:= setweight(to_tsvector('english',text_result), 'A');
                ELSIF attribute.order=2 THEN
                    set_weight:= setweight(to_tsvector('english',text_result), 'B');
                ELSIF attribute.order=3 THEN
                    set_weight:= setweight(to_tsvector('english',text_result), 'C');
                ELSE
                    set_weight:= setweight(to_tsvector('english',text_result), 'D');
                END IF;          
            final_text:=final_text || set_weight || ' ';
          
      END LOOP;
      UPDATE client_template.entity_base SET search_index =  final_text WHERE entity_id = ent_id;
END;$$;


--
-- Name: FUNCTION build_entity_idx_col(ent_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION build_entity_idx_col(ent_id bigint) IS 'Input : entity id

Return Type:  tsvector(This type is used to build the index fro search functionality)

Functionality : This prodecure bulds the search index column for the entity_base table.This is an internal function to the trigger text_search_on_data on the attribute_value_storage_base table.

Steps Involved : 
      1)  With the entity id from the input we pull all the records from the attribute_value_storage_base 
           table with the entity id.We iterate over each of the attribute value.
      2)  Depending upon the data type we will pick the value column.
      3)  We prepare a string and assign weight to the string.
      4)  The loop continues with 2 and 3 steps untill all the attribute values are formed into one string.
      5)  We return the string prepared in the 1-4 steps. 
      
      Note : We only index the attributes where the attribute indexed column is set to TRUE.';


--
-- Name: build_storage_idx_col(attribute_value_storage_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION build_storage_idx_col(avs attribute_value_storage_base) RETURNS tsvector
    LANGUAGE plpgsql
    AS $$
DECLARE
     att_value client_template.attribute_base;
     ent_type bigint;
     text_result text;
     final_text tsvector;
     vec_val tsvector;
BEGIN
     final_text:='';
     SELECT INTO ent_type entity_type_id FROM ONLY client_template.entity_base WHERE entity_id = avs.entity_id;
     SELECT * INTO att_value FROM ONLY client_template.attribute_base WHERE entity_type_id = ent_type AND attribute_id = avs.attribute_id;
            IF att_value.indexed = TRUE THEN
                IF att_value.attribute_datatype_id = 1 THEN
                       text_result:= avs.value_varchar;
                 ELSIF att_value.attribute_datatype_id = 2 THEN
                       text_result:= avs.value_long;
                 ELSIF att_value.attribute_datatype_id = 3 THEN
                       text_result:= avs.value_date;
                 ELSIF att_value.attribute_datatype_id = 4 THEN
                       text_result:= avs.value_text;
                 ELSIF att_value.attribute_datatype_id = 5 THEN
                       text_result:= avs.value_time;
                 ELSE
                     RAISE EXCEPTION 'Did not find the type'; 
                END IF;                    
            END IF;
      vec_val := to_tsvector('english',text_result) ;
      final_text:=final_text || vec_val || ' ';    
      RETURN final_text;
END;$$;


--
-- Name: FUNCTION build_storage_idx_col(avs attribute_value_storage_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION build_storage_idx_col(avs attribute_value_storage_base) IS 'Input : entity id

Return Type:  tsvector(This type is used to build the index fro search functionality)

Functionality : This prodecure bulds the search index column for the attribute_value_storage_base table.This is an internal function to the trigger text_search_on_storage_data on the attribute_value_storage_base table.

Steps Involved : 
      1)  With the entity id from the input we pull the record from the attribute_value_storage_base 
           table with the entity id.
      2)  Depending upon the data type we will pick the value column.
      3)  We prepare a string and assign weight to the string.
      5)  We return the string prepared in the 2&3 steps. 
      
      Note : We only index the attributes where the attribute indexed column is set to TRUE.';


--
-- Name: check_attribute_datatype(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_attribute_datatype() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	attribute_data_type bigint;
BEGIN
        SELECT INTO attribute_data_type attribute_datatype_id FROM client_template.attribute_base where attribute_id = NEW.attribute_id;
	IF attribute_data_type <> 2 THEN
		RAISE EXCEPTION 'You cannot save a custom attribute which is not linked to a custom attribute';
	END IF;
RETURN NEW;
END;$$;


--
-- Name: check_attribute_order(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_attribute_order() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
     aids client_template.attribute_base;
     exists_order integer;
     total_count_order integer;
     new_order integer;
BEGIN
     IF (TG_OP = 'INSERT') THEN

        SELECT INTO  total_count_order  COUNT(*)  from ONLY client_template.attribute_base where entity_type_id = NEW.entity_type_id ;
        SELECT INTO exists_order count(*) from only client_template.attribute_base where "order"= NEW.order  and entity_type_id = NEW.entity_type_id;
        IF exists_order = 0 OR total_count_order = 0 THEN
             exists_order := total_count_order+1;
             NEW.order := exists_order;   
        ELSE
             new_order := NEW.order;
             FOR aids IN SELECT * from ONLY client_template.attribute_base where entity_type_id = NEW.entity_type_id and "order" >= NEW.order order by "order" LOOP
                 new_order := new_order+1;
                 UPDATE client_template.attribute_base set "order"=new_order  WHERE attribute_id = aids.attribute_id ;    
             END LOOP;
        END IF;   
        RETURN NEW;
     ELSIF (TG_OP = 'DELETE') THEN
        FOR aids IN SELECT * from ONLY client_template.attribute_base where entity_type_id = OLD.entity_type_id and "order" > OLD.order order by "order" LOOP
            new_order := aids.order;
            new_order := new_order - 1;
            UPDATE client_template.attribute_base set "order"=new_order  WHERE attribute_id = aids.attribute_id ;    
        END LOOP;
        RETURN NEW;
     END IF;
       
   
END;$$;


--
-- Name: FUNCTION check_attribute_order(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_attribute_order() IS 'Functionality : This trigger ensures to maintain the attribute order in a sorted way while performing insert and delete operation on the attributes.
';


--
-- Name: check_attribute_value_data_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_attribute_value_data_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	dtid bigint;
BEGIN
	-- Check attribute_value_storage data
	IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
		SELECT INTO dtid datatype_id FROM ONLY client_template.attribute_datatype_base dt, client_template.attribute_base att WHERE att.attribute_id = NEW.attribute_id and att.attribute_datatype_id = dt.datatype_id;
		IF ((dtid=1) AND (NEW.value_varchar IS NULL OR  NEW.value_long IS NOT NULL OR NEW.value_date IS NOT NULL OR NEW.value_time IS NOT NULL OR NEW.value_text IS NOT NULL)) THEN 			
			RAISE EXCEPTION 'Value varchar can not be null and other values should be null';		     
		ELSIF (( dtid=2) AND (NEW.value_long IS NULL OR NEW.value_varchar IS NOT NULL OR NEW.value_date IS NOT NULL OR NEW.value_time IS NOT NULL OR NEW.value_text IS NOT NULL)) THEN 			
			RAISE EXCEPTION 'Value long can not be null and other values should be null';	      
		ELSIF (( dtid=3) AND (NEW.value_date IS NULL OR NEW.value_varchar IS NOT NULL OR NEW.value_long IS NOT NULL OR NEW.value_time IS NOT NULL OR NEW.value_text IS NOT NULL)) THEN 			
			RAISE EXCEPTION 'Value date can not be null and other values should be null';	      
		ELSIF (( dtid=4) AND (NEW.value_text IS NULL OR NEW.value_varchar IS NOT NULL OR NEW.value_long IS NOT NULL OR NEW.value_date IS NOT NULL OR NEW.value_time IS NOT NULL)) THEN 			
			RAISE EXCEPTION 'Value text can not be null and other values should be null';
		ELSIF (( dtid=5) AND (NEW.value_time IS NULL OR NEW.value_varchar IS NOT NULL OR NEW.value_long IS NOT NULL OR NEW.value_date IS NOT NULL OR NEW.value_text IS NOT NULL)) THEN 			
			RAISE EXCEPTION 'Value time can not be null and other values should be null';
		END IF;
	END IF;
        RETURN NEW;	           	
END;$$;


--
-- Name: FUNCTION check_attribute_value_data_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_attribute_value_data_base() IS 'Functionality : This trigger check the value columns of the attribute_value_storage_base table to ensure that only one value column have the data at any given instance.';


--
-- Name: check_authority(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_authority() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    role_authority varchar;
BEGIN
    -- Check whether role exists
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        SELECT INTO role_authority value_varchar from client_template.attribute_value_storage_base where attribute_id = 130 AND value_varchar=NEW.authority;
         IF role_authority IS NULL THEN
            RAISE EXCEPTION 'Role does not exist';
         END IF;
    END IF;
    RETURN NEW;                 
END;$$;


--
-- Name: FUNCTION check_authority(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_authority() IS 'Functionality : This trigger checks if the role has an authority in the attribute_value_strorage_base.';


--
-- Name: check_dup_user_name(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_dup_user_name() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
BEGIN
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
       IF NEW.attribute_id = 125 THEN
          SELECT INTO ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 AND value_varchar LIKE NEW.value_varchar;
          IF NOT FOUND THEN
          ELSE
            RAISE EXCEPTION 'User Already Exist';
          END IF;
       END IF;   
    END IF;
    RETURN NEW;                 
END;$$;


--
-- Name: check_entity_type(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_entity_type() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_vals client_template.entity_type_base;
BEGIN
    -- Check whether entity type exists
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        SELECT * INTO ent_vals FROM ONLY client_template.entity_type_base WHERE entity_type_id = NEW.entity_type_id ;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'Enity Type does not exists';
         END IF;
    END IF;
    RETURN NEW;                 
END;$$;


--
-- Name: FUNCTION check_entity_type(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_entity_type() IS 'Functinality : checks the entity_type_base table for the existence of the entity type on Insert or Update.';


--
-- Name: check_entity_valid(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_entity_valid() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
ent_vals client_template.entity_base;
att_vals client_template.attribute_base;
att_id bigint;
valid boolean;

BEGIN
     valid = TRUE;
     SELECT * INTO ent_vals FROM ONLY client_template.entity_base WHERE entity_id = NEW.entity_id AND entity_valid = FALSE ;
     IF NOT FOUND THEN
     ELSE
         FOR att_vals IN SELECT * FROM ONLY client_template.attribute_base WHERE entity_type_id = ent_vals.entity_type_id AND required = TRUE LOOP
             SELECT INTO att_id attribute_id FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = NEW.entity_id AND attribute_id = att_vals.attribute_id;
             IF NOT FOUND THEN
                valid = FALSE;
             END IF;   
         END LOOP;
         IF valid = TRUE THEN
             UPDATE  client_template.entity_base SET entity_valid = TRUE WHERE entity_id = NEW.entity_id AND entity_type_id = ent_vals.entity_type_id;
         END IF;    
     END IF;
     RETURN NEW;
END;
$$;


--
-- Name: FUNCTION check_entity_valid(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_entity_valid() IS 'Functinality : This trigger operates on the attribute_value_storage_base table.The main purpose it to set the  entity_valid to true of an entity in the entity_base table when all of the required attributes of an entity are not null in the attribute_value_storage_base table.';


--
-- Name: check_file_name(attribute_value_file_storage_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_file_name(att_file_val attribute_value_file_storage_base) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
DECLARE
f_name character varying;
names text[];
fname text;
inc bigint;
ctr bigint;
temp text[];
afv client_template.attribute_value_file_storage_base;
chk_int character varying;
BEGIN    
         inc:=1;
         afv := att_file_val;
         SELECT INTO f_name name FROM ONLY client_template.attribute_value_file_storage_base WHERE lower(name)= lower(att_file_val.name) AND attribute_id = att_file_val.attribute_id AND entity_id = att_file_val.entity_id;
         IF NOT FOUND THEN
             RETURN att_file_val.name;
         ELSE 
             fname := f_name;
             fname :=replace(fname , '_', ' _ ');
             fname :=replace(fname , '.', ' . '); 
             IF (fname LIKE '% _ % . %') THEN
                 fname :=replace(fname , ' _ ', '_');
                 temp  :=  regexp_split_to_array(fname, ' . ');
                 names := regexp_split_to_array(temp[1], '_');
                 ctr:= array_upper(names,1);
                 chk_int:= names[ctr];
                 IF chk_int ~ '^[0-9]' THEN
                     inc := names[ctr];
                     inc := inc+1;
                     names[ctr] := inc;
                     fname := array_to_string(names, '_');
                     fname := fname ||'.'||temp[2] ;
                 ELSE
                     fname := temp[1] ||'_'||inc ||'.'||temp[2] ;
                 END IF;
                 SELECT INTO f_name name FROM ONLY client_template.attribute_value_file_storage_base WHERE lower(name)= lower(fname) AND attribute_id = att_file_val.attribute_id AND entity_id = att_file_val.entity_id;
                 IF NOT FOUND THEN
                    RETURN fname; 
                 ELSE
                    afv.name := f_name;
                    f_name := client_template.check_file_name(afv);
                    RETURN f_name; 
                 END IF;   

             ELSE
                 temp  :=  regexp_split_to_array(fname, ' . ');
                 fname := temp[1] ||'_'||inc||'.'||temp[2] ; 
                 SELECT INTO f_name name FROM ONLY client_template.attribute_value_file_storage_base WHERE lower(name)= lower(fname) AND attribute_id = att_file_val.attribute_id AND entity_id = att_file_val.entity_id;
                 IF NOT FOUND THEN
                    RETURN fname; 
                 ELSE
                    afv.name := f_name;
                    f_name := client_template.check_file_name(afv);
                    RETURN f_name; 
                 END IF;   
             END IF;       
         END IF;
         RETURN fname;
END;$$;


--
-- Name: FUNCTION check_file_name(att_file_val attribute_value_file_storage_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_file_name(att_file_val attribute_value_file_storage_base) IS 'Input : Object of attribute_value_file_storage_base Table.
Return Type : Character Varying ( name)
Functionality : This procedures checks the file name before saving new file or updating an existing file whetther any file name exist with the current file name to be saved on the same entity id and attribute id combination, if so the name is appended with a number to differentiate with the already existing file name.

EX: If new file name is  jason.text
       If another file name with same name and extention is found on the same entity id and attribute id then
       the name is appended with number.
   
        jason.text  --> jason_1.text.

';


--
-- Name: check_filename(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_filename() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
     IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
         NEW.name := client_template.check_file_name(NEW.*);
     END IF;
RETURN NEW;
END;$$;


--
-- Name: FUNCTION check_filename(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_filename() IS 'Functionality : This trigger call the inertnal function check_file_name() to check if there is any file name with the current file name on the same entity id and attribute id.';


--
-- Name: check_user_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_user_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	user_name varchar;
BEGIN
	-- Check whether user exists
	IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
		SELECT INTO user_name value_varchar FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 AND value_varchar = NEW.mod_user;
		IF user_name IS NULL THEN
			RAISE EXCEPTION 'User name does not exists';
		END IF;
	END IF;
        RETURN NEW;	           	
END;$$;


--
-- Name: FUNCTION check_user_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_user_base() IS 'Functionality : This trigger check for the existance of the user in the People entity type on theattribute_value_storage_base table.This is important because in our system only an existing user can only save or update data on any table.So this is a common trigger used on most of the tables in our data base.';


--
-- Name: check_user_name(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION check_user_name() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    user_name varchar;
BEGIN
    -- Check whether user exists
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        SELECT INTO user_name value_varchar from client_template.attribute_value_storage_base WHERE attribute_id = 125 AND value_varchar=NEW.username;
         IF user_name IS NULL THEN
            RAISE EXCEPTION 'User does not exists';
         END IF;
    END IF;
    RETURN NEW;                 
END;$$;


--
-- Name: FUNCTION check_user_name(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION check_user_name() IS 'Functionality : This trigger check for the existance of the user in the People entity type on theattribute_value_storage_base table.This is important because in our system only an existing user can only save or update data on any table.So this is a common trigger used on most of the tables in our data base.';


--
-- Name: delete_custom_regex(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION delete_custom_regex() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
  reg_id bigint;
BEGIN
  SELECT INTO reg_id regex_id FROM ONLY client_template.regex_base WHERE regex_id = OLD.regex_id AND custom = TRUE;
  IF NOT FOUND THEN
  ELSE
      DELETE FROM ONLY client_template.regex_base WHERE regex_id = OLD.regex_id AND custom = TRUE;
  END IF;    
  RETURN NEW;
END;$$;


--
-- Name: FUNCTION delete_custom_regex(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION delete_custom_regex() IS 'Functionality : This procedure deletes custom regex from the  regex_base table where the custom is set to true to the regex id we want to delete. ';


--
-- Name: entities_box_count(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entities_box_count(username character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE
 b_id bigint;
 user_ent_id bigint;
 cnt bigint;

BEGIN
         SELECT INTO user_ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = username;
         IF NOT FOUND THEN
               RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';
         ELSE              
               SELECT count(*) INTO cnt FROM ONLY client_template.box_base_entity_base boxent,client_template.box_base box WHERE boxent.box_id=box.box_id and box.user_entity_id = user_ent_id;
         RETURN cnt;
               
         END IF;   
         
END;$$;


--
-- Name: FUNCTION entities_box_count(username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entities_box_count(username character varying) IS 'Input : user name
Return type : count of box entities associted with the user name(bigint)
Functionality : This procedure returns count of all the box entities foa a given user.
';

--
-- Name: attribute_value_file_storage_id; Type: Column; Schema: client_template; Owner: -
--

ALTER TABLE attribute_value_storage_base
 ADD attribute_value_file_storage_id bigint NULL;

--
-- Name: complete_entity_base; Type: VIEW; Schema: client_template; Owner: -
--

CREATE VIEW complete_entity_base AS
    SELECT DISTINCT ent.entity_type_name, ent.entity_type_id, ent.template, ent.edit_template, ent.lock_mode, att.attribute_id, att."order", att.attribute_name, att.attribute_datatype_id, (SELECT dtb.datatype_name FROM ONLY attribute_datatype_base dtb WHERE (dtb.datatype_id = att.attribute_datatype_id)) AS attribute_datatype_name, att.attribute_fieldtype_id, (SELECT flt.fieldtype_name FROM ONLY attribute_fieldtype_base flt WHERE (flt.fieldtype_id = att.attribute_fieldtype_id)) AS attribute_fieldtype_name, att.indexed, att.required, att.mod_user, en.entity_id, en.entity_valid, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(attval.attribute_value_id, attval.attribute_id, attval.entity_id, attval.value_varchar, attval.value_long, attval.value_date, attval.value_time, attval.value_text, attval.mod_user, attval.search_index, attval.attribute_value_file_storage_id))) AS textin FROM ((ONLY attribute_value_storage_base attval JOIN ONLY entity_base et1 ON ((attval.entity_id = et1.entity_id))) LEFT JOIN ONLY entity_type_base ent1 ON ((ent1.entity_type_id = att.entity_type_id))) WHERE ((att.attribute_id = attval.attribute_id) AND (attval.entity_id = en.entity_id)))) AS value_storage, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(reg.regex_id, reg.pattern, reg.display_name, reg.custom))) AS textin FROM ONLY regex_base reg WHERE (reg.regex_id = att.regex_id))) AS regex_pattern, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(afo.fieldtype_option_id, afo.attribute_fieldtype_id, afo.option, afv.option_value_id, afv.attribute_id, afv.value))) AS textin FROM ((ONLY attribute_fieldtype_option_base afo JOIN ONLY attribute_fieldtype_base af ON ((af.fieldtype_id = afo.attribute_fieldtype_id))) LEFT JOIN ONLY attribute_fieldtype_option_value_base afv ON ((afv.attribute_fieldtype_option_id = afo.fieldtype_option_id))) WHERE ((att.attribute_fieldtype_id = af.fieldtype_id) AND (att.attribute_id = afv.attribute_id)))) AS fieldtype_option_value, (SELECT DISTINCT ARRAY(SELECT textin(record_out(ROW(flval.attribute_value_file_storage_id, flval.attribute_id, flval.entity_id, flval.name, flval.description, flval.sort, flval.mod_user))) AS textin FROM (ONLY attribute_value_file_storage_base flval JOIN ONLY entity_base et1 ON ((flval.entity_id = et1.entity_id))) WHERE ((att.attribute_id = flval.attribute_id) AND (flval.entity_id = en.entity_id)))) AS file_storage, att.display, att.deletable AS attribute_deletable, ent.deletable AS entitytype_deletable, ent.treeable AS entitytype_treeable  FROM ONLY entity_base en, ONLY attribute_base att, ONLY entity_type_base ent WHERE ((en.entity_type_id = ent.entity_type_id) AND (att.entity_type_id = ent.entity_type_id)) ORDER BY en.entity_id, att."order";


--
-- Name: entities_invalid_load(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entities_invalid_load(user_name character varying) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
ent_id bigint;
BEGIN
   IF user_name = NULL THEN
       RAISE EXCEPTION 'YOU MUST PASS USER NAME';
   END IF;

   RETURN QUERY SELECT * FROM ONLY client_template.complete_entity_base WHERE mod_user = user_name AND entity_valid = FALSE;
   
END;$$;


--
-- Name: entities_load(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entities_load() RETURNS SETOF complete_entity_base
    LANGUAGE sql
    AS $$SELECT * FROM client_template.complete_entity_base$$;


--
-- Name: FUNCTION entities_load(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entities_load() IS 'Input : None
Return Type : complete_entity_base(view)
Functionality : This procedure returns all the entities from the complet_entity_base view.';


--
-- Name: entities_saved_search_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entities_saved_search_load(id bigint) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$
DECLARE 
result_data client_template.complete_entity_base;
BEGIN
         FOR result_data IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = id LOOP
             RETURN NEXT result_data;
         END LOOP;
END;$$;


--
-- Name: FUNCTION entities_saved_search_load(id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entities_saved_search_load(id bigint) IS 'Input : entity type id
Return type : complete_entity_base (view)
Functionality : This procedure loads all the entities from the complete_entity_base view that matches the given entity type id from the input';


--
-- Name: entity_box_delete(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_box_delete(ent_id bigint, username character varying) RETURNS bigint
    LANGUAGE plpgsql
    AS $$
DECLARE
  b_id bigint;
  cnt bigint;
  user_ent_id bigint;

BEGIN
	  SELECT INTO user_ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = username;
	     IF NOT FOUND THEN
		RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';
             ELSE
		SELECT INTO b_id box_id FROM ONLY client_template.box_base WHERE  user_entity_id = user_ent_id;
		    IF NOT FOUND THEN
                       RAISE EXCEPTION 'NO data to delete';
                    ELSE
                       DELETE FROM client_template.box_base_entity_base WHERE box_id = b_id AND entity_id = ent_id;                      
                    END IF; 
             END IF;
             SELECT count(*) INTO cnt FROM ONLY client_template.box_base_entity_base where box_id = b_id;
	     RETURN cnt;   
         
END;$$;


--
-- Name: FUNCTION entity_box_delete(ent_id bigint, username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_box_delete(ent_id bigint, username character varying) IS 'Input :entity id ,user name.
Return Type :count(count of remaing box ids after deletion)
Functionality :This procedure deletes entity from the user box.
Steps Involved : 
 1)  With user name from the input we get the user entity id from the attribute_value_storage_base.
 2)  With the user entity id from step 1, we get the box id from the box base table.
 3)  With the box id from step 2  and entity id from input ,we delete the entity from the box_base_entity_base table.
 4)  We return the count of enties remaining in the box for the user.  ';


--
-- Name: entity_box_load(character varying, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_box_load(user_name character varying, order_ent character varying, num_lmt bigint, num_ofset bigint) RETURNS SETOF public.complete_entity_box_id_count
    LANGUAGE plpgsql
    AS $$DECLARE
   user_ent_id bigint;
   box_entity client_template.box_base_entity_base;
   result_data public.complete_entity_box_id_count;  
   cnt bigint;
BEGIN
   IF user_name = NULL THEN
       RAISE EXCEPTION 'YOU MUST PASS USER NAME';
   END IF;
   SELECT INTO user_ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = user_name;
   IF NOT FOUND THEN
        RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';
   ELSE         
         SELECT count(*) INTO cnt FROM ONLY client_template.box_base_entity_base boxent,client_template.box_base box WHERE boxent.box_id=box.box_id and box.user_entity_id = user_ent_id;
   END IF;  
   FOR box_entity IN SELECT * FROM ONLY client_template.box_base_entity_base boxent,client_template.box_base box WHERE boxent.box_id=box.box_id and box.user_entity_id = user_ent_id LIMIT num_lmt OFFSET num_ofset LOOP            
           FOR result_data  IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_id = box_entity.entity_id ORDER BY order_ent LOOP
               result_data.box_id := box_entity.box_id ;
               result_data.result_count := cnt;
               RETURN NEXT result_data ;
            END LOOP;      
   END LOOP;
END;$$;


--
-- Name: FUNCTION entity_box_load(user_name character varying, order_ent character varying, num_lmt bigint, num_ofset bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_box_load(user_name character varying, order_ent character varying, num_lmt bigint, num_ofset bigint) IS 'Input : User name,order(Ascending or Descending),limit,offset
Return Type : complete_entity_box_id_count(Custom Type)
Functinality : This function loads all the box entities associated with an user.
Steps Involed : 
 1)  With the user name from the input we get the entity id of the user from the attribute_value_storage_base.
 2) The entity id of the user is used to get the actual enties added to his box.for this we query the box_base table ,here we get the box ids associted with the user entity id with these box ids we go the box_base_entity_base table and pull all the entity ids associted with the box ids that belongs to the user.
';


--
-- Name: box_base_entity_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE box_base_entity_base (
    box_id bigint NOT NULL,
    entity_id bigint NOT NULL
);


--
-- Name: entity_box_load_entity_ids(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_box_load_entity_ids(user_name character varying) RETURNS SETOF box_base_entity_base
    LANGUAGE plpgsql
    AS $$
DECLARE
e_id bigint;
bx_id bigint;
BEGIN

     IF user_name = NULL THEN
        RAISE EXCEPTION 'YOU MUST PASS USER NAME';
     END IF;

     SELECT INTO e_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = user_name;
     IF NOT FOUND THEN
         RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';

     ELSE
         SELECT INTO bx_id box_id FROM ONLY client_template.box_base WHERE user_entity_id = e_id;
         IF NOT FOUND THEN
            
         ELSE
            RETURN QUERY  SELECT * FROM ONLY client_template.box_base_entity_base WHERE box_id = bx_id ;
         END IF;     
     END IF;

END;$$;


--
-- Name: FUNCTION entity_box_load_entity_ids(user_name character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_box_load_entity_ids(user_name character varying) IS 'Input : User name
Return Type : box_base_entity_base.
Functinality : This function loads all the box entities associated with an user.
Steps Involed : 
 1)  With the user name from the input we get the entity id of the user from the attribute_value_storage_base.
 2) The entity id of the user is used to get the actual enties added to his box.for this we query the box_base table ,here we get the box ids associted with the user entity id with these box ids we go the box_base_entity_base table and pull all the entity ids associted with the box ids that belongs to the user.
';


--
-- Name: entity_box_loadall(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_box_loadall(user_name character varying) RETURNS SETOF public.complete_entity_box_id_count
    LANGUAGE plpgsql
    AS $$DECLARE
   user_ent_id bigint;
   box_entity client_template.box_base_entity_base;
   result_data public.complete_entity_box_id_count;  
   cnt bigint;
BEGIN
   IF user_name = NULL THEN
       RAISE EXCEPTION 'YOU MUST PASS USER NAME';
   END IF;
   SELECT INTO user_ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = user_name;
   IF NOT FOUND THEN
        RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';
   ELSE         
         SELECT count(*) INTO cnt FROM ONLY client_template.box_base_entity_base boxent,client_template.box_base box WHERE boxent.box_id=box.box_id and box.user_entity_id = user_ent_id;
   END IF;  
   FOR box_entity IN SELECT * FROM ONLY client_template.box_base_entity_base boxent,client_template.box_base box WHERE boxent.box_id=box.box_id and box.user_entity_id = user_ent_id LOOP            
           FOR result_data  IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_id = box_entity.entity_id LOOP
               result_data.box_id := box_entity.box_id ;
               result_data.result_count := cnt;
               RETURN NEXT result_data ;
            END LOOP;      
   END LOOP;
END;$$;


--
-- Name: FUNCTION entity_box_loadall(user_name character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_box_loadall(user_name character varying) IS 'Input : User name.
Return Type : complete_entity_box_id_count(Custom Type)
Functinality : This function loads all the box entities associated with an user.
Steps Involed : 
 1)  With the user name from the input we get the entity id of the user from the attribute_value_storage_base.
 2) The entity id of the user is used to get the actual enties added to his box.for this we query the box_base table ,here we get the box ids associted with the user entity id with these box ids we go the box_base_entity_base table and pull all the entity ids associted with the box ids that belongs to the user.
';


--
-- Name: entity_box_save(bigint[], character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_box_save(ent_id bigint[], user_name character varying) RETURNS SETOF box_base_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
e_id bigint;
box_entity_id bigint;
ent_value client_template.attribute_value_storage_base;
save_data client_template.box_base;
box_entity client_template.box_base_entity_base;
id bigint;
BEGIN

     IF user_name = NULL THEN
        RAISE EXCEPTION 'YOU MUST PASS USER NAME';
     END IF;

     SELECT INTO e_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 and value_varchar = user_name;
     IF NOT FOUND THEN
         RAISE EXCEPTION 'USER ENTITY ID DOES NOT EXIST';
     ELSE
         SELECT INTO id box_id FROM ONLY client_template.box_base WHERE user_entity_id = e_id;
         IF NOT FOUND THEN
            SELECT INTO save_data.box_id nextval('client_' || 'template.box_' || 'base_box_id_seq');
            save_data.user_entity_id := e_id;
            INSERT INTO client_template.box_base VALUES(save_data.*);
         ELSE
             save_data.box_id := id;
             save_data.user_entity_id := e_id;
         END IF;    
         FOR i IN COALESCE(array_lower(ent_id,1),0) .. COALESCE(array_upper(ent_id,1),-1) LOOP
             SELECT * INTO ent_value FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_id[i];
             IF NOT FOUND THEN
                RAISE EXCEPTION 'ENTITY DOES NOT EXIST';
             ELSE
                SELECT * INTO box_entity FROM ONLY client_template.box_base_entity_base WHERE box_id = save_data.box_id AND entity_id = ent_id[i];
                IF NOT FOUND THEN
                   INSERT INTO client_template.box_base_entity_base VALUES(save_data.box_id,ent_id[i]);
                ELSE
                END IF;   
             END IF;      
         END LOOP;
         RETURN QUERY  SELECT * FROM ONLY client_template.box_base_entity_base WHERE box_id = save_data.box_id ;
     END IF;

    

END;$$;


--
-- Name: FUNCTION entity_box_save(ent_id bigint[], user_name character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_box_save(ent_id bigint[], user_name character varying) IS 'Input : Array of entity ids, user name. 
Return Type : box_base_entity_base table.
Functinality : This procedure saves the entities to the user  box .
     Steps Involed : 
                1)  We get the entity id of the user with user name from the input on attribute_value_storage_base table
                2)  We check if there is a box id with the user entity id.
                     If we find it then we store the box id for further use in the comming steps.
                     If we dont find it then we create a new box id for the user entity id.
                3)  We iterate over the array of entity ids from the input and save each entity with box id into the box_base_entity_base table.   
                4) Once we saved all the entites we will return all the rows from the box_base_entity_base table with the box id we just created.';


--
-- Name: bulk_entity_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE bulk_entity_base (
    counter bigint
)
INHERITS (entity_base, attribute_value_storage_base);


--
-- Name: entity_bulk_save(bulk_entity_base[], character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_bulk_save(ent_storage_values bulk_entity_base[], universal_id character varying, prim_entype_id bigint, start_number bigint) RETURNS public.import_status
    LANGUAGE plpgsql
    AS $$
DECLARE
    num_attempted bigint;
    ent_storage_value_rec client_template.bulk_entity_base;
    entity client_template.entity_base;
    rel_entity client_template.entity_base;
    attribute_value_storage_rec client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    currentcounter client_template.bulk_entity_base;
    currentCounterHasError boolean;
    reason character varying;
    previouscounter bigint;
    records_failed bigint;
    num_failed bigint;
    end_time timestamp with time zone;
    cnt bigint;
    ent_ids bigint[];
    ent_id bigint;
    entids_saved text;
    imp_status public.import_status;
BEGIN
    IF ent_storage_values = NULL or COALESCE(array_lower(ent_storage_values,1),0) < 1 THEN
          RAISE EXCEPTION 'You should pass in entity storage values to save';
    END IF;
    IF universal_id IS NULL THEN
          RAISE EXCEPTION 'You should pass the Universal Unique ID';
    END IF;
    entids_saved:='';   
    num_attempted := 0; 
    records_failed := 0;
    SELECT INTO rel_entity null; 
    --INSERT INTO client_template.import_base (import_id,number_attempted, time_start)VALUES (universal_id,num_attempted, now());              
    FOR i IN COALESCE(array_lower(ent_storage_values,1),0) .. COALESCE(array_upper(ent_storage_values,1),-1) LOOP

              ent_storage_value_rec := ent_storage_values[i];
              currentcounter.counter := ent_storage_value_rec.counter;
              IF ent_storage_value_rec.entity_type_id = prim_entype_id THEN
                 IF entity IS NULL THEN
                    entity.entity_type_id := ent_storage_value_rec.entity_type_id;
                    entity.mod_user := ent_storage_value_rec.mod_user; 
                    entity.entity_id := client_template.entity_save_return_entityid(entity);
                    attribute_value_storage_rec.entity_id := entity.entity_id;
                    ent_ids := array_append(ent_ids, entity.entity_id);
                 END IF;
                 IF rel_entity IS NULL THEN 
                 ELSE
                    attribute_value_storage_rec.entity_id := entity.entity_id;
                    ent_storage_value_rec.value_varchar := rel_entity.entity_id;
                    rel_entity := NULL;  
                 END IF;
              ELSE
                  
                  IF rel_entity IS NULL THEN
                    rel_entity.entity_type_id := ent_storage_value_rec.entity_type_id;
                    rel_entity.mod_user := ent_storage_value_rec.mod_user; 
                    rel_entity.entity_id := client_template.entity_save_return_entityid(rel_entity);
                    attribute_value_storage_rec.entity_id := rel_entity.entity_id;
                    ent_ids := array_append(ent_ids, rel_entity.entity_id);
                 END IF;     
              END IF; 
                  
              attribute_value_storage_rec.attribute_value_id := ent_storage_value_rec.attribute_value_id ;
              attribute_value_storage_rec.attribute_id :=  ent_storage_value_rec.attribute_id;
              attribute_value_storage_rec.value_varchar := ent_storage_value_rec.value_varchar;
              attribute_value_storage_rec.value_long := ent_storage_value_rec.value_long;
              attribute_value_storage_rec.value_date := ent_storage_value_rec.value_date ;
              attribute_value_storage_rec.value_text := ent_storage_value_rec.value_text;
              attribute_value_storage_rec.mod_user := ent_storage_value_rec.mod_user ;

              avs[i]:= attribute_value_storage_rec;
              IF currentcounter.counter <> ent_storage_values[i+1].counter OR array_upper(ent_storage_values,1) = i THEN
                  BEGIN 
                    num_attempted := num_attempted+1;
                    PERFORM client_template.attribute_values_save_returnvoid(avs);
                     EXCEPTION
                       WHEN unique_violation THEN
                               records_failed := records_failed+1;
                               INSERT INTO client_template.import_failed_row_base VALUES (nextval('client_' || 'template.import_failed_row_b' || 'ase_id_seq'), universal_id, currentcounter.counter, SQLERRM, ent_storage_value_rec);
                       WHEN OTHERS THEN
                               records_failed := records_failed+1;
                               INSERT INTO client_template.import_failed_row_base VALUES (nextval('client_' || 'template.import_failed_row_b' || 'ase_id_seq'), universal_id, currentcounter.counter, SQLERRM, ent_storage_value_rec);
                  END;
                   SELECT INTO entity null;
                   avs=null;
              END IF;
    END LOOP;
    FOR i IN COALESCE(array_lower(ent_ids,1),0) .. COALESCE(array_upper(ent_ids,1),-1) LOOP
        ent_id := ent_ids[i];
        PERFORM client_template.build_entity_idx_col(ent_id);
    END LOOP;
    entids_saved := array_to_string(ent_ids,',');
    imp_status.ent_ids := entids_saved;
    imp_status.num_attempted := num_attempted;
    imp_status.num_failed := records_failed;
    --SELECT INTO records_failed COUNT(*) from client_template.import_failed_row_base WHERE import_id = universal_id;
    --SELECT INTO cnt number_attempted FROM ONLY client_template.import_base WHERE  import_id = universal_id;            
    --UPDATE client_template.import_base SET number_attempted = num_attempted+cnt ,number_failed = records_failed WHERE  import_id = universal_id;
    --RETURN QUERY SELECT * FROM ONLY client_template.import_failed_row_base WHERE  import_id = universal_id;
    
    RETURN imp_status;
END;$$;


--
-- Name: entity_file_storage_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_file_storage_load(ent_id bigint) RETURNS SETOF attribute_value_file_storage_base
    LANGUAGE plpgsql
    AS $$BEGIN
     RETURN QUERY SELECT * FROM ONLY client_template.attribute_value_file_storage_base WHERE entity_id = ent_id;
END;$$;


--
-- Name: FUNCTION entity_file_storage_load(ent_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_file_storage_load(ent_id bigint) IS 'Input : entity id
Return Type: attribute_value_file_storage_base Table.
Funtionality : Loads all the rows from the attribute_value_file_storage_base Table that matches the entity id from the input.';


--
-- Name: entity_import_status(character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_import_status(universal_id character varying, no_attempted bigint, no_failed bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
DECLARE
     imp_bs client_template.import_base;
BEGIN
     SELECT * INTO imp_bs FROM ONLY client_template.import_base WHERE import_id = universal_id;
     IF NOT FOUND THEN
        INSERT INTO client_template.import_base (import_id,number_attempted, number_failed, time_start)VALUES (universal_id,no_attempted, no_failed, now());
     ELSE
        UPDATE client_template.import_base SET number_attempted = no_attempted+imp_bs.number_attempted ,number_failed = no_failed+imp_bs.number_failed,time_ended = NULL WHERE  import_id = universal_id;
     END IF;
END;$$;


--
-- Name: entity_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_load(ent_id bigint) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$BEGIN
RETURN QUERY SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_id = ent_id;
END;$$;


--
-- Name: entity_load_report(bigint, bigint[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_load_report(ent_typ_id bigint, att_ids bigint[]) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$
DECLARE 
result_data client_template.complete_entity_base;
temp bigint[];
BEGIN
         FOR result_data IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = ent_typ_id LOOP
            temp[1] := result_data.attribute_id;
            IF (att_ids @> temp) THEN
                  RETURN NEXT result_data;
            END IF;
         END LOOP;
END;$$;


--
-- Name: entity_save(entity_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_save(entities_to_save entity_base[]) RETURNS SETOF entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
	entity_to_save client_template.entity_base; 
	eid bigint;
BEGIN
	IF entities_to_save IS NULL or COALESCE(array_lower(entities_to_save,1),0) < 1 THEN
		RAISE EXCEPTION 'You must pass in entity types to save';
	END IF;
	FOR i IN COALESCE(array_lower(entities_to_save,1),0) .. COALESCE(array_upper(entities_to_save,1),-1) LOOP
		entity_to_save:=entities_to_save[i];
		IF entity_to_save.mod_user IS NULL THEN
		       RAISE EXCEPTION 'You must pass user name';
	        END IF; 
		IF entity_to_save.entity_id IS NULL or entity_to_save.entity_id = 0 THEN
			SELECT INTO entity_to_save.entity_id nextval('client_' || 'template.entity_entity_id_seq');
			INSERT INTO client_template.entity_base VALUES(entity_to_save.*)
			RETURNING * INTO entity_to_save;
		ELSE
			SELECT INTO eid entity_id from client_template.entity_base WHERE entity_id = entity_to_save.entity_id;
			IF NOT FOUND THEN
				RAISE EXCEPTION 'One or more entities do not exist to update';
			ELSE 
				UPDATE client_template.entity_base SET entity_id=entity_to_save.entity_id, entity_type_id=entity_to_save.entity_type_id, mod_user=entity_to_save.mod_user WHERE entity_id=entity_to_save.entity_id;
			END IF;
		END IF;
	END LOOP;
	FOR entity_to_save IN SELECT * FROM ONLY client_template.entity_base LOOP
		RETURN NEXT entity_to_save;
	END LOOP;
END;$$;


--
-- Name: FUNCTION entity_save(entities_to_save entity_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_save(entities_to_save entity_base[]) IS 'Input : Array of objects from entity_base table.
Return Type : entity_base Table.
Functionality : This procedure creates or updates an entity. We iterate over each object of the array from input ,we check if the entity is already existed if so we will update the corresponding entity row in the entity_base table otherwise we will create new entity by getting the sequence number from the sequence table (entity_entity_id_seq).Once the iteration over we return all the rows from entity base table. 
';


--
-- Name: entity_save_return_entityid(entity_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_save_return_entityid(entity_to_save entity_base) RETURNS bigint
    LANGUAGE plpgsql
    AS $$DECLARE
	eid bigint;
BEGIN
		
	IF entity_to_save.mod_user IS NULL THEN
	   RAISE EXCEPTION 'You must pass user name';
	END IF; 
	IF  entity_to_save.entity_id IS NULL or entity_to_save.entity_id = 0 THEN
	    SELECT INTO entity_to_save.entity_id nextval('client_' || 'template.entity_entity_id_seq');
            entity_to_save.entity_valid := FALSE;
	    INSERT INTO client_template.entity_base VALUES(entity_to_save.*);
	ELSE
	    SELECT INTO eid entity_id from client_template.entity_base WHERE entity_id = entity_to_save.entity_id;
		IF NOT FOUND THEN
		   RAISE EXCEPTION 'One or more entities do not exist to update';
		ELSE 
		   UPDATE client_template.entity_base SET entity_id=entity_to_save.entity_id, entity_type_id=entity_to_save.entity_type_id, mod_user=entity_to_save.mod_user,entity_valid =entity_to_save.entity_valid WHERE entity_id=entity_to_save.entity_id;
		END IF;
	END IF;
	RETURN entity_to_save.entity_id;
END;$$;


--
-- Name: entity_search(character varying[], bigint, character varying, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_search(search_terms character varying[], ent_type_id bigint, highlight_start character varying, highlight_stop character varying, num_lmt bigint, num_ofset bigint) RETURNS SETOF public.complete_entity_search_count
    LANGUAGE plpgsql
    AS $_$DECLARE
    result_data public.complete_entity_search_count;
    att_values client_template.attribute_value_storage_base;
    at_vals client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    search_data text;
    text_result text;
    ent_values client_template.entity_base;
    start_tag character varying;
    end_tag character varying;
    highlight_pattern character varying;
    rs_count bigint;
    cnt1 bigint;
    cnt2 bigint;
BEGIN
    search_data :='' ;
    FOR i IN COALESCE(array_lower(search_terms,1),0) .. COALESCE(array_upper(search_terms,1),-1) LOOP
       text_result := search_terms[i];
       IF (text_result  NOT LIKE '$%$')
       THEN
           --RAISE EXCEPTION 'data';
           --search_data := replace(text_result , '$ ', '');
           --search_data := replace(search_data , ' $', '');
           --search_data := replace(search_data , ' ', ' & ');
           --search_data := client_template.internal_entity_search(search_data);
       --ELSE    
           IF length(search_data) > 0 THEN
              search_data := search_data || ' | ' || text_result||':*';
           ELSE 
              search_data := text_result||':*';              
           END IF;
           search_data := client_template.internal_entity_search(search_data);
       END IF;   
    END LOOP;

    start_tag := 'StartSel = ?';
    end_tag := 'StopSel = ?';
    start_tag := replace(start_tag, '?', highlight_start);
    end_tag := replace(end_tag, '?', highlight_stop);
    highlight_pattern := start_tag || ',' || end_tag;
    rs_count := 0;
    IF ent_type_id > 0 THEN
          FOR ent_values IN SELECT * FROM ONLY client_template.entity_base WHERE entity_type_id = ent_type_id AND entity_valid = TRUE ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
          SELECT count(*) INTO rs_count FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id;
          rs_count := rs_count+rs_count;
          SELECT *, ts_rank_cd(search_index , to_tsquery(search_data)) INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ORDER BY 10 DESC;
          FOR att_values IN (SELECT avsj.*,ts_rank_cd(search_index , to_tsquery(search_data)) FROM ONLY client_template.attribute_value_storage_base avsj, client_template.attribute_base att WHERE att.attribute_id = avsj.attribute_id AND att.attribute_fieldtype_id <> 5 AND att.indexed = TRUE AND search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ORDER BY entity_id, att.order)
              UNION ALL
              (SELECT avsj.*, "order" FROM ONLY client_template.attribute_value_storage_base avsj,client_template.attribute_base att WHERE att.attribute_id = avsj.attribute_id AND att.attribute_fieldtype_id <> 5 AND att.indexed = TRUE AND entity_id = ent_values.entity_id AND avsj.attribute_id  NOT IN (SELECT avsj.attribute_id
              FROM ONLY client_template.attribute_value_storage_base avsj WHERE search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ) ORDER BY entity_id, "order") LOOP
                 avs[1] := client_template.internal_entity_search_highlighting(att_values,search_data,highlight_pattern);
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_type_id = ent_type_id AND entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = true;
                 result_data.value_storage[1] := avs[1];
                 result_data.result_count := rs_count;
                 RETURN NEXT result_data;
             END LOOP;             
         END LOOP;
    ELSE
          FOR ent_values IN SELECT entity_id FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(search_data)  AND entity_valid = TRUE ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
          SELECT count(*) INTO rs_count FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(search_data);
          SELECT *, ts_rank_cd(search_index , to_tsquery(search_data)) INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(search_data) ORDER BY 10 DESC;
          FOR att_values IN (SELECT avsj.*, "order" FROM ONLY client_template.attribute_value_storage_base avsj,client_template.attribute_base att WHERE
          att.attribute_id = avsj.attribute_id AND att.attribute_fieldtype_id <> 5 AND att.indexed = TRUE AND search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ORDER BY entity_id, "order")
             UNION ALL
             (SELECT avsj.*, "order" FROM ONLY client_template.attribute_value_storage_base avsj,client_template.attribute_base att WHERE att.attribute_id = avsj.attribute_id AND att.indexed = TRUE AND att.attribute_fieldtype_id <> 5 AND entity_id = ent_values.entity_id AND avsj.attribute_id  NOT IN (SELECT avsj.attribute_id
             FROM ONLY client_template.attribute_value_storage_base avsj WHERE search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ) ORDER BY entity_id, "order") LOOP
                 avs[1] := client_template.internal_entity_search_highlighting(att_values,search_data,highlight_pattern);
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id ;--AND display = true;
                 result_data.value_storage[1] := avs[1];
                 result_data.result_count := rs_count;
                 RETURN NEXT result_data;
             END LOOP;
             SELECT INTO result_data NULL;
          END LOOP;   
    END IF;
    IF (text_result  LIKE '$%$')
       THEN
          
           search_data := replace(text_result , '$', '');
           search_data := replace(search_data , '$', '');
           search_data := trim(both ' ' from search_data);
           --search_data := replace(search_data , ' ', ' & ');
           --search_data := client_template.internal_entity_search(search_data);
           SELECT COUNT(*) INTO cnt1 FROM ONLY client_template.attribute_value_storage_base atvs  WHERE POSITION(search_data IN value_varchar)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id);
           SELECT COUNT(*)INTO cnt2 FROM ONLY client_template.attribute_value_storage_base atvs WHERE POSITION(search_data IN value_text)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id);
           rs_count := cnt1+cnt2;
           FOR att_values IN (SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE POSITION(search_data IN value_varchar)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id))
           UNION ALL
           (SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE POSITION(search_data IN value_text)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id)) LOOP
                 search_data := replace(search_data , ' ', ' & ');
                 avs[1] := client_template.internal_entity_search_highlighting(att_values,search_data,highlight_pattern);
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = true;
                 result_data.value_storage[1] := avs[1];
                 result_data.result_count := rs_count;
                 RETURN NEXT result_data;
           END LOOP;
           
    END IF;     
 END;$_$;


--
-- Name: entity_search_complex(character varying[], character varying, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_search_complex(search_terms character varying[], highlight_start character varying, highlight_stop character varying, num_lmt bigint, num_ofset bigint) RETURNS SETOF public.complete_entity_search_count
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_typ_ids bigint[];
    ct1_search_data text;
    ct1_att_ids bigint[];
    ct2_search_data_arr character varying[];
    ct2_att_ids bigint[];
    ct3_att_ids bigint[];
    ct3_search_data_arr character varying[];
    c4_search_data character varying;
    c4_search_att_data character varying;
    ct1_rs_count bigint;
    ct4_rs_count bigint;
    gn_rs_count bigint;
    ent_value client_template.entity_base;
    att_value_temp client_template.attribute_value_storage_base;
    att_id bigint[];
    gn_search_data character varying;
    att_values client_template.attribute_value_storage_base;
    att_value client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    result_data public.complete_entity_search_count;
    fn_result_data public.complete_entity_search_count[];
    att_tbl client_template.attribute_base;
    start_tag character varying;
    end_tag character varying;
    highlight_pattern character varying;
    en_id bigint;
    etype_id bigint;
    oprt character varying[];
    cntl bigint;
    att_strg_val client_template.attribute_value_storage_base;
    enty_ids client_template.entity_base;
    srch_data public.complex_entity_search_type ;
    flg boolean;
    flgs boolean;
    inc bigint;
    ins bigint;
    test boolean;
    prev_ent_id bigint;
    gn_fnrs_count bigint;
    gn_prev_count bigint;
    srt public.complete_entity_search_count;
    srts bigint;
    k int;
    bs_ent_ids bigint[];
    bs_ent_id bigint;
    bs_rs_count bigint;
    bs_att_ids bigint[];
    bs_data_arr character varying[];
    tmp_ent_ids bigint[];
    tmp_ent_id bigint;
    rs_count bigint;
BEGIN
    srch_data := client_template.internal_entity_search_complex_mod(search_terms);
    ent_typ_ids := srch_data.entity_type_ids;
    ct1_search_data := srch_data.ct1_sdata; 
    ct1_att_ids := srch_data.ct1_attids;
    ct2_search_data_arr := srch_data.ct2_sdata;
    ct2_att_ids := srch_data.ct2_attids;
    ct3_search_data_arr := srch_data.ct3_sdata;
    ct3_att_ids := srch_data.ct3_attids;
    c4_search_data := srch_data.ct4_sdata;
    c4_search_att_data := srch_data.ct4_attdata; 
    att_id := srch_data.ct3_oprts;
    oprt := srch_data.ct2_oprts;
    gn_search_data := srch_data.gn_sdata ;
    start_tag := 'StartSel = ?';
    end_tag := 'StopSel = ?';
    start_tag := replace(start_tag, '?', highlight_start);
    end_tag := replace(end_tag, '?', highlight_stop);
    highlight_pattern := start_tag || ',' || end_tag;
    IF array_length(ent_typ_ids ,1) > 0 THEN
        gn_fnrs_count := 0;
        gn_prev_count := 0; 
        FOR i IN COALESCE(array_lower(ent_typ_ids,1),0) .. COALESCE(array_upper(ent_typ_ids,1),-1) LOOP
          en_id := ent_typ_ids[i];
          IF array_length(ct1_att_ids,1) > 0 THEN
             FOR i IN COALESCE(array_lower(ct1_att_ids,1),0) .. COALESCE(array_upper(ct1_att_ids,1),-1) LOOP
                SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = ct1_att_ids[i] and entity_type_id = en_id;
                IF NOT FOUND THEN
                ELSE
                    SELECT count(*) INTO ct1_rs_count FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id);
                    FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id) ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
                        avs[1] := client_template.internal_entity_search_highlighting(att_values,ct1_search_data,highlight_pattern);
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_type_id = en_id AND entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = TRUE;
                        result_data.value_storage[1] := avs[1];
                        result_data.result_count := ct1_rs_count;
                        RETURN NEXT result_data;
                    END LOOP;    
                END IF;
             END LOOP;
          END IF;
         IF (array_length(ct2_att_ids,1) > 0) OR (array_length(ct3_att_ids,1) > 0) THEN
              IF (array_length(ct2_att_ids,1) > 0) THEN
                 bs_att_ids := ct2_att_ids;
                 bs_data_arr:= ct2_search_data_arr;
              END IF;
              IF (array_length(ct3_att_ids,1) > 0) THEN
                 bs_att_ids := ct3_att_ids;
                 bs_data_arr:= ct3_search_data_arr;
              END IF;
              bs_rs_count := client_template.internal_entity_search_complex_getcnt(bs_att_ids,bs_data_arr,oprt,en_id);
              bs_ent_ids := NULL;
              inc := 1;
              FOR i IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                  SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = bs_att_ids[i] AND entity_type_id = en_id;
                  IF NOT FOUND THEN
                  ELSE
                     IF bs_ent_id IS NULL THEN
                        k:=1;
                        FOR bs_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i] LIMIT num_lmt OFFSET num_ofset LOOP
                            bs_ent_ids[k] := bs_ent_id;
                            k := k+1;
                        END LOOP;
                        IF array_length(bs_ent_ids,1) > 0 THEN
                        ELSE
                           IF (COALESCE(oprt[i]) LIKE 'and') OR (COALESCE(oprt[i]) LIKE 'not') THEN
                               RAISE EXCEPTION 'exit ';
                           END IF;        
                        END IF;
                     ELSE 
                         k:=1;
                         FOR tmp_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i] LIMIT num_lmt OFFSET num_ofset LOOP
                            tmp_ent_ids[k] := tmp_ent_id;
                            k := k+1;
                         END LOOP;
                         IF array_length(tmp_ent_ids,1) > 0 THEN
                            IF (COALESCE(oprt[i-1]) LIKE 'and') THEN
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) INTERSECT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'not') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) EXCEPT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'or') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                            ELSE
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                         END IF;     
                     END IF;
                 END IF;
                 END IF;
              END LOOP;
              FOR i IN COALESCE(array_lower(bs_ent_ids,1),0) .. COALESCE(array_upper(bs_ent_ids,1),-1) LOOP
                  FOR j IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                      SELECT * INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = bs_ent_ids[i] AND attribute_id = bs_att_ids[j];
                      avs[1] := client_template.internal_entity_search_highlighting(att_values,bs_data_arr[j],highlight_pattern);
                      SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = TRUE AND entity_valid = TRUE;
                      result_data.value_storage[1] := avs[1];
                      result_data.result_count := bs_rs_count;
                      IF result_data.entity_id IS NULL THEN
                      ELSE
                         RETURN NEXT result_data;
                      END IF;   
                   END LOOP;
              END LOOP;
         END IF;
         IF gn_search_data IS NULL THEN
                   IF (ct1_att_ids IS NULL) AND (ct2_att_ids IS NULL) AND (ct3_att_ids IS NULL) THEN
                      IF (gn_fnrs_count = 0 ) AND (gn_prev_count = 0) THEN
                         FOR i IN COALESCE(array_lower(ent_typ_ids,1),0) .. COALESCE(array_upper(ent_typ_ids,1),-1) LOOP
                           SELECT count(DISTINCT(entity_id)) INTO gn_rs_count FROM ONLY client_template.entity_base WHERE entity_type_id = ent_typ_ids[i] AND entity_valid = TRUE;
                            gn_fnrs_count := gn_fnrs_count+gn_rs_count;
                            gn_prev_count := gn_fnrs_count;
                         END LOOP;
                      END IF; 
                      FOR result_data IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = en_id AND indexed = TRUE AND display = TRUE AND entity_valid = TRUE ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
                          result_data.result_count := gn_fnrs_count;
                          RETURN NEXT result_data;
                      END LOOP;
                    END IF;
          ELSE     
                    IF (gn_fnrs_count = 0 ) AND (gn_prev_count = 0) THEN
                       FOR i IN COALESCE(array_lower(ent_typ_ids,1),0) .. COALESCE(array_upper(ent_typ_ids,1),-1) LOOP
                           SELECT count(*) INTO gn_rs_count FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(gn_search_data) AND entity_type_id = ent_typ_ids[i] AND entity_valid = TRUE;
                            gn_fnrs_count := gn_fnrs_count+gn_rs_count;
                            gn_prev_count := gn_fnrs_count;
                       END LOOP;
                    END IF;   
                    FOR enty_ids IN SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(gn_search_data) AND entity_type_id = en_id AND entity_valid = TRUE ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
                      FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(gn_search_data) AND entity_id = enty_ids.entity_id LOOP
                        avs[1] := client_template.internal_entity_search_highlighting(att_values,gn_search_data,highlight_pattern);
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE  entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = TRUE ;
                        result_data.value_storage[1] := avs[1];
                        result_data.result_count := gn_fnrs_count;
                        RETURN NEXT result_data;
                      END LOOP;
                   END LOOP;
          END IF;
        END LOOP;
    ELSE  
          IF array_length(ct1_att_ids,1) > 0 THEN
             FOR i IN COALESCE(array_lower(ct1_att_ids,1),0) .. COALESCE(array_upper(ct1_att_ids,1),-1) LOOP
                SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = ct1_att_ids[i];
                IF NOT FOUND THEN
                ELSE
                    SELECT count(*) INTO ct1_rs_count FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id);
                    FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id) ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
                        avs[1] := client_template.internal_entity_search_highlighting(att_values,ct1_search_data,highlight_pattern);
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = TRUE;
                        result_data.value_storage[1] := avs[1];
                        result_data.result_count := ct1_rs_count;
                        RETURN NEXT result_data;
                    END LOOP;    
                END IF;
             END LOOP;
          END IF;

         IF (array_length(ct2_att_ids,1) > 0) OR (array_length(ct3_att_ids,1) > 0) THEN
              IF (array_length(ct2_att_ids,1) > 0) THEN
                 bs_att_ids := ct2_att_ids;
                 bs_data_arr:= ct2_search_data_arr;
              END IF;
              IF (array_length(ct3_att_ids,1) > 0) THEN
                 bs_att_ids := ct3_att_ids;
                 bs_data_arr:= ct3_search_data_arr;
              END IF;
              bs_rs_count := client_template.internal_entity_search_complex_getcnt(bs_att_ids,bs_data_arr,oprt,en_id);
              bs_ent_ids := NULL;
              inc := 1;
              FOR i IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                  SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = bs_att_ids[i];
                  IF NOT FOUND THEN
                  ELSE
                     IF bs_ent_id IS NULL THEN
                        k:=1;
                        FOR bs_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i] LIMIT num_lmt OFFSET num_ofset LOOP
                            bs_ent_ids[k] := bs_ent_id;
                            k := k+1;
                        END LOOP;
                        IF array_length(bs_ent_ids,1) > 0 THEN
                        ELSE
                           IF (COALESCE(oprt[i]) LIKE 'and') OR (COALESCE(oprt[i]) LIKE 'not') THEN
                               RAISE EXCEPTION 'exit ';
                           END IF;        
                        END IF;
                     ELSE 
                         k:=1;
                         FOR tmp_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i] LIMIT num_lmt OFFSET num_ofset LOOP
                            tmp_ent_ids[k] := tmp_ent_id;
                            k := k+1;
                         END LOOP;
                         IF array_length(tmp_ent_ids,1) > 0 THEN
                            IF (COALESCE(oprt[i-1]) LIKE 'and') THEN
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) INTERSECT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'not') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) EXCEPT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'or') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                            ELSE
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                         END IF;     
                     END IF;
                  END IF;
                END IF;
              END LOOP;
              FOR i IN COALESCE(array_lower(bs_ent_ids,1),0) .. COALESCE(array_upper(bs_ent_ids,1),-1) LOOP
                  FOR j IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                      SELECT * INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = bs_ent_ids[i] AND attribute_id = bs_att_ids[j];
                      avs[1] := client_template.internal_entity_search_highlighting(att_values,bs_data_arr[j],highlight_pattern);
                      SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = att_values.entity_id AND attribute_id = att_values.attribute_id AND display = TRUE AND entity_valid = TRUE;
                      result_data.value_storage[1] := avs[1];
                      result_data.result_count := bs_rs_count;
                      IF result_data.entity_id IS NULL THEN
                      ELSE
                         RETURN NEXT result_data;
                      END IF;   
                   END LOOP;
              END LOOP;
         END IF;
         IF array_length(att_id,1) > 0 THEN
             test := true;
             prev_ent_id := 0;
             ct4_rs_count := 0;
             FOR i IN COALESCE(array_lower(att_id,1),0) .. COALESCE(array_upper(att_id,1),-1) LOOP
                  IF test = true THEN
                      FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(c4_search_data) AND entity_valid = TRUE LOOP
                          SELECT * INTO att_value_temp FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(c4_search_att_data) AND entity_id = ent_value.entity_id AND attribute_id = att_id[i];
                          IF NOT FOUND THEN
                           ELSE
                           FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                             SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = ent_value.entity_id AND attribute_id = att_value.attribute_id AND display = TRUE;
                             IF (prev_ent_id = 0) OR (prev_ent_id <> result_data.entity_id) THEN
                                 prev_ent_id := result_data.entity_id;
                                 ct4_rs_count := ct4_rs_count+1;  
                             END IF;      
                           END LOOP;
                          END IF;   
                      END LOOP;
                      test := false;
                   END IF ;
            FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(c4_search_data) AND entity_valid = TRUE ORDER BY entity_id ASC LIMIT num_lmt OFFSET num_ofset LOOP
                SELECT * INTO att_value_temp FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(c4_search_att_data) AND entity_id = ent_value.entity_id AND attribute_id = att_id[i];
                IF NOT FOUND THEN
                ELSE
                   FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                     avs[1] := client_template.internal_entity_search_highlighting(att_value,c4_search_data,highlight_pattern);
                     SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = ent_value.entity_id AND attribute_id = att_value.attribute_id AND display = TRUE;
                     result_data.value_storage[1] := avs[1];
                     result_data.result_count := ct4_rs_count;
                     RETURN NEXT result_data;
                   END LOOP;
                END IF;   
            END LOOP;
          END LOOP; 
         END IF;
        END IF;

END;$$;


--
-- Name: entity_search_complex_rtn_enttyp(character varying[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_search_complex_rtn_enttyp(search_terms character varying[]) RETURNS SETOF complete_entity_type_base
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_typ_ids bigint[];
    ct1_search_data text;
    ct1_att_ids bigint[];
    ct2_search_data_arr character varying[];
    ct2_att_ids bigint[];
    ct3_att_ids bigint[];
    ct3_search_data_arr character varying[];
    c4_search_data character varying;
    c4_search_att_data character varying;
    ct1_rs_count bigint;
    ct4_rs_count bigint;
    gn_rs_count bigint;
    ent_value client_template.entity_base;
    att_value_temp client_template.attribute_value_storage_base;
    att_id bigint[];
    gn_search_data character varying;
    att_values client_template.attribute_value_storage_base;
    att_value client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    result_data client_template.complete_entity_type_base;
    fn_result_data public.complete_entity_search_count[];
    att_tbl client_template.attribute_base;
    en_id bigint;
    etype_id bigint;
    oprt character varying[];
    cntl bigint;
    att_strg_val client_template.attribute_value_storage_base;
    enty_ids client_template.entity_base;
    srch_data public.complex_entity_search_type ;
    flg boolean;
    flgs boolean;
    inc bigint;
    ins bigint;
    test boolean;
    prev_ent_id bigint;
    gn_fnrs_count bigint;
    gn_prev_count bigint;
    srt public.complete_entity_search_count;
    srts bigint;
    k int;
    bs_ent_ids bigint[];
    bs_ent_id bigint;
    bs_rs_count bigint;
    bs_att_ids bigint[];
    bs_data_arr character varying[];
    tmp_ent_ids bigint[];
    tmp_ent_id bigint;
    rs_count bigint;
    temp_arr bigint[];
    temp_arr1 bigint[];
    cnt bigint;
BEGIN
    srch_data := client_template.internal_entity_search_complex_mod(search_terms);
    ent_typ_ids := srch_data.entity_type_ids;
    ct1_search_data := srch_data.ct1_sdata; 
    ct1_att_ids := srch_data.ct1_attids;
    ct2_search_data_arr := srch_data.ct2_sdata;
    ct2_att_ids := srch_data.ct2_attids;
    ct3_search_data_arr := srch_data.ct3_sdata;
    ct3_att_ids := srch_data.ct3_attids;
    c4_search_data := srch_data.ct4_sdata;
    c4_search_att_data := srch_data.ct4_attdata; 
    att_id := srch_data.ct3_oprts;
    oprt := srch_data.ct2_oprts;
    gn_search_data := srch_data.gn_sdata ;
    cnt := 1;
    IF array_length(ent_typ_ids ,1) > 0 THEN
        gn_fnrs_count := 0;
        gn_prev_count := 0; 
        FOR i IN COALESCE(array_lower(ent_typ_ids,1),0) .. COALESCE(array_upper(ent_typ_ids,1),-1) LOOP
          en_id := ent_typ_ids[i];
          IF array_length(ct1_att_ids,1) > 0 THEN
             FOR i IN COALESCE(array_lower(ct1_att_ids,1),0) .. COALESCE(array_upper(ct1_att_ids,1),-1) LOOP
                SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = ct1_att_ids[i] and entity_type_id = en_id;
                IF NOT FOUND THEN
                ELSE
                    FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id) ORDER BY entity_id ASC  LOOP
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE entity_type_id = en_id  AND attribute_id = att_values.attribute_id AND display = TRUE;
                        IF cnt = 1 THEN
                           temp_arr[cnt] := result_data.entity_type_id;
                           cnt := cnt+1;
                           RETURN NEXT result_data;
                        ELSE 
                           temp_arr1[1] := result_data.entity_type_id;
                           IF (temp_arr @> temp_arr1) THEN
                           ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                           END IF;   
                        END IF; 
                    END LOOP;    
                END IF;
             END LOOP;
          END IF;
         IF (array_length(ct2_att_ids,1) > 0) OR (array_length(ct3_att_ids,1) > 0) THEN
              IF (array_length(ct2_att_ids,1) > 0) THEN
                 bs_att_ids := ct2_att_ids;
                 bs_data_arr:= ct2_search_data_arr;
              END IF;
              IF (array_length(ct3_att_ids,1) > 0) THEN
                 bs_att_ids := ct3_att_ids;
                 bs_data_arr:= ct3_search_data_arr;
              END IF;
              bs_rs_count := client_template.internal_entity_search_complex_getcnt(bs_att_ids,bs_data_arr,oprt,en_id);
              bs_ent_ids := NULL;
              inc := 1;
              FOR i IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                  SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = bs_att_ids[i] AND entity_type_id = en_id;
                  IF NOT FOUND THEN
                  ELSE
                     IF bs_ent_id IS NULL THEN
                        k:=1;
                        FOR bs_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i]  LOOP
                            bs_ent_ids[k] := bs_ent_id;
                            k := k+1;
                        END LOOP;
                        IF array_length(bs_ent_ids,1) > 0 THEN
                        ELSE
                           IF (COALESCE(oprt[i]) LIKE 'and') OR (COALESCE(oprt[i]) LIKE 'not') THEN
                               RAISE EXCEPTION 'exit ';
                           END IF;        
                        END IF;
                     ELSE 
                         k:=1;
                         FOR tmp_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i]  LOOP
                            tmp_ent_ids[k] := tmp_ent_id;
                            k := k+1;
                         END LOOP;
                         IF array_length(tmp_ent_ids,1) > 0 THEN
                            IF (COALESCE(oprt[i-1]) LIKE 'and') THEN
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) INTERSECT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'not') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) EXCEPT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'or') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                            ELSE
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                         END IF;     
                     END IF;
                 END IF;
                 END IF;
              END LOOP;
              FOR i IN COALESCE(array_lower(bs_ent_ids,1),0) .. COALESCE(array_upper(bs_ent_ids,1),-1) LOOP
                  FOR j IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                      SELECT * INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = bs_ent_ids[i] AND attribute_id = bs_att_ids[j];
                      SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_values.attribute_id AND display = TRUE AND entity_valid = TRUE;
                      IF result_data.entity_id IS NULL THEN
                      ELSE
                       IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                       ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                       END IF; 
                      END IF;   
                   END LOOP;
              END LOOP;
         END IF;
         IF gn_search_data IS NULL THEN
                   IF (ct1_att_ids IS NULL) AND (ct2_att_ids IS NULL) AND (ct3_att_ids IS NULL) THEN
                      FOR result_data IN SELECT * FROM ONLY client_template.complete_entity_type_base WHERE entity_type_id = en_id AND indexed = TRUE LOOP
                       IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                       ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                       END IF; 
                      END LOOP;
                    END IF;
          ELSE     
                    FOR enty_ids IN SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(gn_search_data) AND entity_type_id = en_id AND entity_valid = TRUE ORDER BY entity_id ASC  LOOP
                      FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(gn_search_data) AND entity_id = enty_ids.entity_id LOOP
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE  attribute_id = att_values.attribute_id ;
                       IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                       ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                       END IF; 
                     END LOOP;
                   END LOOP;
          END IF;
        END LOOP;
    ELSE  
          IF array_length(ct1_att_ids,1) > 0 THEN
             FOR i IN COALESCE(array_lower(ct1_att_ids,1),0) .. COALESCE(array_upper(ct1_att_ids,1),-1) LOOP
                SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = ct1_att_ids[i];
                IF NOT FOUND THEN
                ELSE
                    SELECT count(*) INTO ct1_rs_count FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id);
                    FOR att_values IN SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(ct1_search_data) and attribute_id = ct1_att_ids[i] AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id) ORDER BY entity_id ASC  LOOP
                        SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_values.attribute_id ;
                        IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                        ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                        END IF; 
                    END LOOP;    
                END IF;
             END LOOP;
          END IF;

         IF (array_length(ct2_att_ids,1) > 0) OR (array_length(ct3_att_ids,1) > 0) THEN
              IF (array_length(ct2_att_ids,1) > 0) THEN
                 bs_att_ids := ct2_att_ids;
                 bs_data_arr:= ct2_search_data_arr;
              END IF;
              IF (array_length(ct3_att_ids,1) > 0) THEN
                 bs_att_ids := ct3_att_ids;
                 bs_data_arr:= ct3_search_data_arr;
              END IF;
              bs_rs_count := client_template.internal_entity_search_complex_getcnt(bs_att_ids,bs_data_arr,oprt,en_id);
              bs_ent_ids := NULL;
              inc := 1;
              FOR i IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                  SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = bs_att_ids[i];
                  IF NOT FOUND THEN
                  ELSE
                     IF bs_ent_id IS NULL THEN
                        k:=1;
                        FOR bs_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i]  LOOP
                            bs_ent_ids[k] := bs_ent_id;
                            k := k+1;
                        END LOOP;
                        IF array_length(bs_ent_ids,1) > 0 THEN
                        ELSE
                           IF (COALESCE(oprt[i]) LIKE 'and') OR (COALESCE(oprt[i]) LIKE 'not') THEN
                               RAISE EXCEPTION 'exit ';
                           END IF;        
                        END IF;
                     ELSE 
                         k:=1;
                         FOR tmp_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(bs_data_arr[i]) AND attribute_id = bs_att_ids[i]  LOOP
                            tmp_ent_ids[k] := tmp_ent_id;
                            k := k+1;
                         END LOOP;
                         IF array_length(tmp_ent_ids,1) > 0 THEN
                            IF (COALESCE(oprt[i-1]) LIKE 'and') THEN
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) INTERSECT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'not') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) EXCEPT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(oprt[i-1]) LIKE 'or') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                            ELSE
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                         END IF;     
                     END IF;
                  END IF;
                END IF;
              END LOOP;
              FOR i IN COALESCE(array_lower(bs_ent_ids,1),0) .. COALESCE(array_upper(bs_ent_ids,1),-1) LOOP
                  FOR j IN COALESCE(array_lower(bs_att_ids,1),0) .. COALESCE(array_upper(bs_att_ids,1),-1) LOOP
                      SELECT * INTO att_values FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = bs_ent_ids[i] AND attribute_id = bs_att_ids[j];
                      SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_values.attribute_id ;
                      IF result_data.entity_type_id IS NULL THEN
                      ELSE
                        IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                        ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                        END IF; 
                      END IF;   
                   END LOOP;
              END LOOP;
         END IF;
         IF array_length(att_id,1) > 0 THEN
             test := true;
             prev_ent_id := 0;
             ct4_rs_count := 0;
             FOR i IN COALESCE(array_lower(att_id,1),0) .. COALESCE(array_upper(att_id,1),-1) LOOP
                  IF test = true THEN
                      FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(c4_search_data) AND entity_valid = TRUE LOOP
                          SELECT * INTO att_value_temp FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(c4_search_att_data) AND entity_id = ent_value.entity_id AND attribute_id = att_id[i];
                          IF NOT FOUND THEN
                           ELSE
                           FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                             SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_value.attribute_id ;
                             IF (prev_ent_id = 0) OR (prev_ent_id <> result_data.entity_id) THEN
                                 prev_ent_id := result_data.entity_type_id;
                             END IF;      
                           END LOOP;
                          END IF;   
                      END LOOP;
                      test := false;
                   END IF ;
            FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(c4_search_data) AND entity_valid = TRUE ORDER BY entity_id ASC  LOOP
                SELECT * INTO att_value_temp FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(c4_search_att_data) AND entity_id = ent_value.entity_id AND attribute_id = att_id[i];
                IF NOT FOUND THEN
                ELSE
                   FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                     SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_value.attribute_id ;
                        IF cnt = 1 THEN
                          temp_arr[cnt] := result_data.entity_type_id;
                          cnt := cnt+1;
                          RETURN NEXT result_data;
                        ELSE 
                          temp_arr1[1] := result_data.entity_type_id;
                          IF (temp_arr @> temp_arr1) THEN
                          ELSE
                             temp_arr[cnt] := result_data.entity_type_id;
                             cnt := cnt+1;
                             RETURN NEXT result_data; 
                          END IF;   
                        END IF; 
                   END LOOP;
                END IF;   
            END LOOP;
          END LOOP; 
         END IF;
        END IF;

END;$$;


--
-- Name: entity_search_rtn_enttype(character varying[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_search_rtn_enttype(search_terms character varying[]) RETURNS SETOF complete_entity_type_base
    LANGUAGE plpgsql
    AS $_$DECLARE
    result_data client_template.complete_entity_type_base;
    att_values client_template.attribute_value_storage_base;
    at_vals client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    search_data text;
    text_result text;
    ent_values client_template.entity_base;
    cnt bigint;
    temp_arr bigint[];
    temp_arr1 bigint[];
    test_arr character varying[];
    srch_typ boolean;

BEGIN
    search_data :='' ;
    cnt := 1;
    test_arr := array[':'];
    srch_typ := FALSE;
    FOR i IN COALESCE(array_lower(search_terms,1),0) .. COALESCE(array_upper(search_terms,1),-1) LOOP
        text_result := search_terms[i];
        IF (text_result LIKE '%[%') OR (text_result LIKE '%:%') OR (text_result LIKE '%]%')THEN
           srch_typ := TRUE;
        END IF;
    END LOOP;
    IF srch_typ THEN
       FOR result_data IN SELECT * FROM client_template.entity_search_complex_rtn_enttyp(search_terms) LOOP
           RETURN NEXT result_data;
       END LOOP;
    ELSE
     FOR i IN COALESCE(array_lower(search_terms,1),0) .. COALESCE(array_upper(search_terms,1),-1) LOOP
       text_result := search_terms[i];
       IF (text_result  NOT LIKE '$%$')
       THEN
           --RAISE EXCEPTION 'data';
           --search_data := replace(text_result , '$ ', '');
           --search_data := replace(search_data , ' $', '');
           --search_data := replace(search_data , ' ', ' & ');
           --search_data := client_template.internal_entity_search(search_data);
       --ELSE    
           IF length(search_data) > 0 THEN
              search_data := search_data || ' | ' || text_result||':*';
           ELSE 
              search_data := text_result||':*';              
           END IF;
           search_data := client_template.internal_entity_search(search_data);
       END IF;   
     END LOOP;

          FOR ent_values IN SELECT entity_id FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(search_data)  AND entity_valid = TRUE ORDER BY entity_id ASC LOOP
          FOR att_values IN (SELECT avsj.*, "order" FROM ONLY client_template.attribute_value_storage_base avsj,client_template.attribute_base att WHERE
          att.attribute_id = avsj.attribute_id AND att.attribute_fieldtype_id <> 5 AND att.indexed = TRUE AND search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ORDER BY entity_id, "order")
             UNION ALL
             (SELECT avsj.*, "order" FROM ONLY client_template.attribute_value_storage_base avsj,client_template.attribute_base att WHERE att.attribute_id = avsj.attribute_id AND att.indexed = TRUE AND att.attribute_fieldtype_id <> 5 AND entity_id = ent_values.entity_id AND avsj.attribute_id  NOT IN (SELECT avsj.attribute_id
             FROM ONLY client_template.attribute_value_storage_base avsj WHERE search_index @@ to_tsquery(search_data) AND entity_id = ent_values.entity_id ) ORDER BY entity_id, "order") LOOP
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_values.attribute_id ;--AND display = true;
                 IF cnt = 1 THEN
                    temp_arr[cnt] := result_data.entity_type_id;
                    cnt := cnt+1;
                    RETURN NEXT result_data;
                 ELSE 
                    temp_arr1[1] := result_data.entity_type_id;
                    IF (temp_arr @> temp_arr1) THEN
                    ELSE
                       temp_arr[cnt] := result_data.entity_type_id;
                       cnt := cnt+1;
                       RETURN NEXT result_data; 
                    END IF;   
                 END IF; 
             END LOOP;
             SELECT INTO result_data NULL;
          END LOOP;   
   
    IF (text_result  LIKE '$%$')
       THEN
          
           search_data := replace(text_result , '$', '');
           search_data := replace(search_data , '$', '');
           search_data := trim(both ' ' from search_data);
           --search_data := replace(search_data , ' ', ' & ');
           --search_data := client_template.internal_entity_search(search_data);
           FOR att_values IN (SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE POSITION(search_data IN value_varchar)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id))
           UNION ALL
           (SELECT * FROM ONLY client_template.attribute_value_storage_base atvs WHERE POSITION(search_data IN value_text)>0 AND entity_id = (SELECT et.entity_id FROM ONLY client_template.entity_base et WHERE entity_valid =  TRUE AND et.entity_id = atvs.entity_id)) LOOP
                 search_data := replace(search_data , ' ', ' & ');
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_type_base WHERE attribute_id = att_values.attribute_id AND display = true;
                 IF cnt = 1 THEN
                    temp_arr[cnt] := result_data.entity_type_id;
                    cnt := cnt+1;
                    RETURN NEXT result_data;
                 ELSE 
                    temp_arr1[1] := result_data.entity_type_id;
                    IF (temp_arr @> temp_arr1) THEN
                    ELSE
                       temp_arr[cnt] := result_data.entity_type_id;
                       cnt := cnt+1;
                       RETURN NEXT result_data; 
                    END IF;   
                 END IF; 

           END LOOP;
           
    END IF;
 END IF;     
 END;$_$;


--
-- Name: entity_search_simple(character varying[], bigint, character varying, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_search_simple(search_terms character varying[], ent_type_id bigint, highlight_start character varying, highlight_stop character varying) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
    result_data client_template.complete_entity_base;
    avs client_template.attribute_value_storage_base[];
    att_value client_template.attribute_value_storage_base;
    ent_value client_template.entity_base;
    search_data text;
    text_result text;
    start_tag character varying;
    end_tag character varying;
    highlight_pattern character varying;
    
BEGIN
    search_data :='' ;
    FOR i IN COALESCE(array_lower(search_terms,1),0) .. COALESCE(array_upper(search_terms,1),-1) LOOP
       text_result := search_terms[i];
       IF length(search_data) > 0 THEN
             search_data := search_data || ' | ' || text_result||':*';
       ELSE 
             search_data := text_result||':*';              
       END IF;
    END LOOP;
    search_data := client_template.internal_entity_search(search_data);
    search_data := client_template.internal_entity_search(search_data);
    start_tag := 'StartSel = ?';
    end_tag := 'StopSel = ?';
    start_tag := replace(start_tag, '?', highlight_start);
    end_tag := replace(end_tag, '?', highlight_stop);
    highlight_pattern := start_tag || ',' || end_tag;

    IF ent_type_id > 0 THEN
         FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(search_data) and entity_type_id = ent_type_id LOOP
             FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                 avs[1] := client_template.internal_entity_search_highlighting(att_value,search_data,highlight_pattern);
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_type_id = ent_type_id AND entity_id = ent_value.entity_id AND attribute_id = att_value.attribute_id;
                 result_data.value_storage[1] := avs[1];
                 RETURN NEXT result_data;
             END LOOP;
         END LOOP;
    ELSE
         FOR ent_value IN  SELECT * FROM ONLY client_template.entity_base WHERE search_index @@ to_tsquery(search_data)LOOP
             FOR att_value IN SELECT * FROM ONLY client_template.attribute_value_storage_base WHERE entity_id = ent_value.entity_id LOOP
                 avs[1] := client_template.internal_entity_search_highlighting(att_value,search_data,highlight_pattern);
                 SELECT * INTO result_data FROM ONLY client_template.complete_entity_base WHERE entity_id = ent_value.entity_id AND attribute_id = att_value.attribute_id;
                 result_data.value_storage[1] := avs[1];
                 RETURN NEXT result_data;
             END LOOP;
         END LOOP;
    END IF;
END;$$;


--
-- Name: entity_set_valid(entity_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_set_valid(entities_to_save entity_base) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
       entity_to_save client_template.entity_base;
       complete_entity client_template.complete_entity_base; 
       eid bigint;
BEGIN
	entity_to_save:=entities_to_save;
        UPDATE client_template.entity_base SET entity_id=entity_to_save.entity_id, entity_type_id=entity_to_save.entity_type_id, mod_user=entity_to_save.mod_user,entity_valid = entity_to_save.entity_valid WHERE entity_id=entity_to_save.entity_id;
        FOR complete_entity IN SELECT * FROM ONLY client_template.complete_entity_base LOOP
             RETURN NEXT complete_entity;
        END LOOP;
END;$$;


--
-- Name: FUNCTION entity_set_valid(entities_to_save entity_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_set_valid(entities_to_save entity_base) IS 'Input : Object of entity base table.
Return Type : complete_entity_base(view)
Functionality : This function updates the entity valid column to true in the entity base table.The pupose of the procedure is to set the entity to valid after the user saves all the required fields on an entity in the entity save form the jsp.';


--
-- Name: entity_type_delete(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_type_delete(ent_type_id bigint, username character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$DECLARE
         eid bigint;
         entity_type_data client_template.entity_type_base; 
         ent_data client_template.entity_type_base; 
BEGIN
      IF username IS NULL THEN
            RAISE EXCEPTION 'You cannot delete entity type without a user name';
      END IF; 
      IF ent_type_id = 0 THEN
            RAISE EXCEPTION 'You must pass in entity type to delete';
      END IF;
      SELECT * INTO ent_data FROM ONLY client_template.entity_type_base WHERE entity_type_id = ent_type_id;
      IF (ent_data.lock_mode IS TRUE) AND (ent_data.deletable IS TRUE) THEN
          SELECT * INTO entity_type_data FROM client_template.entity_type_base  WHERE entity_type_id = ent_type_id and mod_user = username;
          INSERT INTO client_template.audit_entity_type_base  VALUES (nextval('audit_' || 'base_audit_id_seq'), now(), username, 3,entity_type_data.entity_type_id, entity_type_data.entity_type_name );
          DELETE FROM ONLY client_template.entity_type_base WHERE entity_type_id = ent_type_id and mod_user = username;
      ELSE 
          RAISE EXCEPTION 'PLz unlock to delete the entity type OR Check if entity type is deletable'; 
      END IF;       
      END;$$;


--
-- Name: FUNCTION entity_type_delete(ent_type_id bigint, username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_type_delete(ent_type_id bigint, username character varying) IS 'Input : entity type id , user name.
Return Type: void.
Functionality : This procedure takes the entity type id and user name as input and delete the entity type from the entity type base table.Before we delete the entity type we will chesk if the entity type lock mode is opened and aslo it is deletable.Once we delete the entity type we create a row in the audit entity type base table.

Note: Once the entity type is deleted all its attributes in attribute_base table, entities  in entity_base tabe and data related with the entities in attribute_value_storage_base tables are deleted. 
';


--
-- Name: entity_type_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_type_load(ent_typ_id bigint) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$
DECLARE 
result_data client_template.complete_entity_base;
BEGIN
         FOR result_data IN SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = ent_typ_id LOOP
             RETURN NEXT result_data;
         END LOOP;
END;$$;


--
-- Name: FUNCTION entity_type_load(ent_typ_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_type_load(ent_typ_id bigint) IS 'Input : entity type id.
Return Type : complete_entity_base(view)
Functionality : This procedure loads all the rows from the complete_entity_base view that matches the entity type id from the input on the entity type load Jsp.';


--
-- Name: entity_type_set_defaultlock(bigint[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_type_set_defaultlock(ent_type_id bigint[]) RETURNS void
    LANGUAGE plpgsql
    AS $$
BEGIN
    FOR i IN COALESCE(array_lower(ent_type_id,1),0) .. COALESCE(array_upper(ent_type_id,1),-1) LOOP
          UPDATE client_template.entity_type_base SET lock_mode = FALSE WHERE entity_type_id = ent_type_id[i];
    END LOOP;
END;$$;


--
-- Name: FUNCTION entity_type_set_defaultlock(ent_type_id bigint[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_type_set_defaultlock(ent_type_id bigint[]) IS 'Input : entity type id.
Return Type : void.
Function : This procedure set the lock mode of the entity type with the entity type id from the input  to false (locked status).By default the entity type is always locked.';


--
-- Name: entity_type_set_lockmode(bigint, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_type_set_lockmode(ent_type_id bigint, lockmode character varying) RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
lk_mode integer;
lck_mode boolean;
BEGIN
    IF lockmode IS NULL THEN	
        SELECT INTO lck_mode lock_mode FROM ONLY client_template.entity_type_base WHERE entity_type_id = ent_type_id;
        IF lck_mode IS TRUE THEN
		lk_mode :=1;
	ELSE
		lk_mode :=0;
	END IF;
    ELSE
	IF lockmode =  'true' THEN
		lck_mode := TRUE;
	ELSE
		lck_mode :=FALSE;
	END IF;
        UPDATE client_template.entity_type_base SET lock_mode = lck_mode WHERE entity_type_id = ent_type_id;
        SELECT INTO lck_mode lock_mode FROM ONLY client_template.entity_type_base WHERE entity_type_id = ent_type_id;
        IF lck_mode IS TRUE THEN
		lk_mode :=1;
	ELSE
		lk_mode :=0;
	END IF;
    END IF;
    RETURN lk_mode;    
END;$$;


--
-- Name: FUNCTION entity_type_set_lockmode(ent_type_id bigint, lockmode character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_type_set_lockmode(ent_type_id bigint, lockmode character varying) IS 'Input : entity type id , lock mode.
Return Type : Integer.
Functionality : This procedure is used to set the lock mode to toggle between locked and unlocked modes.In the application we can find this functionality on the entity type detai page where we find the toggle button which can lock or unlock the entity type.depending on the action performed on the Jsp the lock mode is updated in the DB.';


--
-- Name: entity_types_save(entity_type_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_types_save(entities_type_to_save entity_type_base[]) RETURNS SETOF complete_entity_type_base
    LANGUAGE plpgsql
    AS $$DECLARE
	entity_type_to_save client_template.entity_type_base; 
	eid bigint;
	complete_type client_template.complete_entity_type_base;
BEGIN
	IF entities_type_to_save IS NULL or COALESCE(array_lower(entities_type_to_save,1),0) < 1 THEN
		RAISE EXCEPTION 'You must pass in entity types to save';
	END IF;
	FOR i IN COALESCE(array_lower(entities_type_to_save,1),0) .. COALESCE(array_upper(entities_type_to_save,1),-1) LOOP
		entity_type_to_save:=entities_type_to_save[i]; 
		IF entity_type_to_save.mod_user IS NULL THEN
		          RAISE EXCEPTION 'You must pass user name';
	        END IF;
		IF entity_type_to_save.entity_type_id IS NULL or entity_type_to_save.entity_type_id = 0 THEN
			SELECT INTO entity_type_to_save.entity_type_id nextval('client_' || 'template.entity_type_id_seq');
			entity_type_to_save.lock_mode := FALSE;
			INSERT INTO client_template.entity_type_base VALUES(entity_type_to_save.*)
			RETURNING * INTO entity_type_to_save;
		ELSE    
		        
			SELECT INTO eid entity_type_id FROM ONLY client_template.entity_type_base WHERE entity_type_id = entity_type_to_save.entity_type_id;
			IF NOT FOUND THEN
			     RAISE EXCEPTION 'One or more entity types do not exist to update';
			ELSE          
                             IF entity_type_to_save.lock_mode IS TRUE THEN
				UPDATE client_template.entity_type_base SET entity_type_name= entity_type_to_save.entity_type_name, mod_user= entity_type_to_save.mod_user , "template" = entity_type_to_save.template, edit_template = entity_type_to_save.edit_template, lock_mode = entity_type_to_save.lock_mode, treeable = entity_type_to_save.treeable WHERE entity_type_id = entity_type_to_save.entity_type_id ;
		             ELSE
		                RAISE EXCEPTION 'PLz unlock to delete or update the entity type';
		             END IF;   		
			END IF;
		END IF;
	END LOOP;
	FOR complete_type IN SELECT * FROM client_template.complete_entity_type_base LOOP
		RETURN NEXT complete_type;
	END LOOP;
END;$$;


--
-- Name: FUNCTION entity_types_save(entities_type_to_save entity_type_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_types_save(entities_type_to_save entity_type_base[]) IS 'Input : Array of objects from entity_type_base table.
Return Type : complete_entity_type_base (view).
Functionality : This procedure creates or updates an entity type. We iterate over each object of the array from input ,we check if the entity type id  is already existed if so we will update the corresponding entity type in the entity_type_base table otherwise we will create new entity type id by getting the sequence number from the sequence table (entity_type_id_seq).Once the iteration over we return complete_entity_type_base view. 

Note: To delete or update an entity type we must make sure that the entity type is unlocked.';


--
-- Name: entity_value_validation(attribute_value_storage_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_value_validation(ent_att_value attribute_value_storage_base) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
att_bs_val client_template.attribute_base;
reg_exp client_template.regex_base;
temp character varying;
BEGIN
     SELECT * INTO att_bs_val FROM ONLY client_template.attribute_base WHERE attribute_id = ent_att_value.attribute_id ;
     IF att_bs_val.regex_id IS NULL THEN
        RETURN TRUE; 
     ELSE
        SELECT * INTO reg_exp FROM ONLY client_template.regex_base WHERE regex_id = att_bs_val.regex_id;
        IF att_bs_val.attribute_datatype_id = 1 THEN
           IF (SELECT ent_att_value.value_varchar ~ reg_exp.pattern) THEN
              RETURN TRUE; 
            ELSE
              RAISE EXCEPTION 'Invalid %',reg_exp.display_name ;    
        END IF;
        ELSIF att_bs_val.attribute_datatype_id = 2 THEN
             temp:= ent_att_value.value_long;
             IF (SELECT temp ~ reg_exp.pattern) THEN
                 RETURN TRUE; 
             ELSE
                 RAISE EXCEPTION 'Invalid %',reg_exp.display_name ;    
             END IF;
         ELSIF att_bs_val.attribute_datatype_id = 3 THEN 
              temp:= ent_att_value.value_date;
              IF (SELECT temp ~ reg_exp.pattern) THEN
                  RETURN TRUE; 
              ELSE
                  RAISE EXCEPTION 'Invalid %',reg_exp.display_name ;    
              END IF;
         ELSIF att_bs_val.attribute_datatype_id = 4 THEN  
         	RETURN TRUE;
              --IF (SELECT ent_att_value.value_text ~ reg_exp.pattern) THEN
                 --RETURN TRUE; 
              --ELSE
                 --RAISE EXCEPTION 'Invalid %',reg_exp.display_name ;    
              --END IF;
         ELSEIF att_bs_val.attribute_datatype_id = 5 THEN  
         	RETURN TRUE;
              --IF (SELECT ent_att_value.value_text ~ reg_exp.pattern) THEN
                 --RETURN TRUE; 
              --ELSE
                 --RAISE EXCEPTION 'Invalid %',reg_exp.display_name ;    
              --END IF;
         ELSE
              RETURN FALSE;
         END IF; 
        END IF;       
END;$$;


--
-- Name: FUNCTION entity_value_validation(ent_att_value attribute_value_storage_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_value_validation(ent_att_value attribute_value_storage_base) IS 'Input : Object of attribute_value_storage_base Table.
Return Type: Boolean.
Functionality : This procedure perfoms validation on the entity data before saving to the attribute_value_storage_base table.To perform validation we will match the data with the regex pattern if it matches then we return true or else we return false. The data is saved only when the validation is true otherwise we raise exception.
Steps Involed:
    1) With th attribute id in the  input object we get the matching attribute data  from the attribute_base table.
    2) We get the regex data from the regex table with the matching regex id we got from the step 1.
    3) Depending on the data type of the attribute from step 1. we perform validation  on particular value cloumn with regex pattern from the regex_base table we got from step 2.If data is valid we will return true or else we return false.';


--
-- Name: entity_values_auto_save(entity_base, attribute_value_storage_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_values_auto_save(entity_save entity_base, entity_values attribute_value_storage_base[]) RETURNS bigint
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
    complete_entity client_template.complete_entity_base;
    entity_value client_template.attribute_value_storage_base;
    entities_to_save client_template.entity_base;
    entity_values_to_save client_template.attribute_value_storage_base[];
BEGIN

        entities_to_save := entity_save; 
        IF (entities_to_save.entity_id IS NULL) OR (entities_to_save.entity_id =0)THEN         
           --SELECT INTO ent_id MAX(entity_id) FROM client_template.entity_save_return_entityid(entities_to_save);
           ent_id := client_template.entity_save_return_entityid(entities_to_save);
          
        ELSE
           ent_id := entities_to_save.entity_id;
        END IF;                  
        FOR i IN COALESCE(array_lower(entity_values,1),0) .. COALESCE(array_upper(entity_values,1),-1) LOOP
            entity_value:= entity_values[i]; 
            IF entity_value.attribute_id = 130 THEN
		entity_value.value_varchar:= 'ROLE_'|| entity_value.value_varchar;
            END IF;
            IF (entity_value.value_varchar IS NULL) AND (entity_value.value_long IS NULL) AND (entity_value.value_date IS NULL) AND (entity_value.value_text IS NULL)   THEN
               EXIT;
            END IF;    
            IF entity_save.entity_id <1 THEN                                          
               entity_value.entity_id:= ent_id;
               entity_values_to_save[i]:=entity_value;
            ELSE
               entity_value.entity_id:= entity_save.entity_id;
               entity_values_to_save[i]:=entity_value;
            END IF;
        END LOOP; 
        PERFORM client_template.attribute_values_save_returnvoid(entity_values_to_save);
        RETURN ent_id;    
END;$$;


--
-- Name: FUNCTION entity_values_auto_save(entity_save entity_base, entity_values attribute_value_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_values_auto_save(entity_save entity_base, entity_values attribute_value_storage_base[]) IS 'Input : Object of entity_base table,Array of objects of attribute_value_storage_base table.
Return type : entity id.
Functionality : This procedure saves entitie to the attribute_value_storage_base table.An entity is a set of values for an entity type with attribues.A set of values for an entity type has the same entity id with differnt attribute ids.An enitty cannot have have duplicate entities in the attribute_value_storage_base table with the same entity id and attribute id.
Steps Involved:
  1) We Will check if the entity is already exit. If existed we will pass on the entity id to further steps.
       If the entity doesnot exist the we will create a new entity in the entity_base table for the entity type 
       to which the data has to be saved.
  2) We iterate over the array of objects of attribute_value_storage_base table and assign entity id we get from step 1.We have to check if any of the value columns have data otherwise we will through exception.
  3) We pass the array of objects of attribute_value_storage_base prepared from the step 2 to an internal function attribute_values_save_returnvoid(). Here the actual data will be saved to the attribute_value_storage table.
 4) We return the entity id.';


--
-- Name: entity_values_save(entity_base, attribute_value_storage_base[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entity_values_save(entity_save entity_base, entity_values attribute_value_storage_base[]) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
    ent_id bigint;
    complete_entity client_template.complete_entity_base;
    entity_value client_template.attribute_value_storage_base;
    entities_to_save client_template.entity_base[];
    entity_values_to_save client_template.attribute_value_storage_base[];
BEGIN
        entities_to_save[1]:= entity_save;               
        SELECT INTO ent_id MAX(entity_id) FROM client_template.entity_save(entities_to_save) ;             
        FOR i IN COALESCE(array_lower(entity_values,1),0) .. COALESCE(array_upper(entity_values,1),-1) LOOP
            entity_value:= entity_values[i];
            IF entity_value.attribute_id = 130 THEN
		entity_value.value_varchar:= 'ROLE_'|| entity_value.value_varchar;
            END IF;     
            IF entity_save.entity_id <1 THEN                                          
               entity_value.entity_id:= ent_id;
               entity_values_to_save[i]:=entity_value;
            ELSE
               entity_value.entity_id:= entity_save.entity_id;
               entity_values_to_save[i]:=entity_value;
            END IF;
        END LOOP; 
        PERFORM client_template.attribute_values_save(entity_values_to_save);    
        FOR complete_entity IN SELECT * FROM ONLY client_template.complete_entity_base LOOP
             RETURN NEXT complete_entity;
        END LOOP;
END;$$;


--
-- Name: FUNCTION entity_values_save(entity_save entity_base, entity_values attribute_value_storage_base[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entity_values_save(entity_save entity_base, entity_values attribute_value_storage_base[]) IS 'Input : Object of entity_base table,Array of objects of attribute_value_storage_base table.
Return type : complete_entity_base(View).
Functionality : This procedure saves entitie to the attribute_value_storage_base table.An entity is a set of values for an entity type with attribues.A set of values for an entity type has the same entity id with differnt attribute ids.An enitty cannot have have duplicate entities in the attribute_value_storage_base table with the same entity id and attribute id.
Steps Involved:
  1) We Will check if the entity is already exit. If existed we will pass on the entity id to further steps.
       If the entity doesnot exist the we will create a new entity in the entity_base table for the entity type 
       to which the data has to be saved.
  2) We iterate over the array of objects of attribute_value_storage_base table and assign entity id we get from step 1.We have to check if any of the value columns have data otherwise we will through exception.
  3) We pass the array of objects of attribute_value_storage_base prepared from the step 2 to an internal function attribute_values_save(). Here the actual data will be saved to the attribute_value_storage table.
  4) We return the complete_entity_base view.';


--
-- Name: entitytype_att_value_load(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entitytype_att_value_load(ent_type_id bigint) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$BEGIN
RETURN QUERY SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = ent_type_id;
END;$$;


--
-- Name: FUNCTION entitytype_att_value_load(ent_type_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entitytype_att_value_load(ent_type_id bigint) IS 'Input : entity type id.
Return type : complete_entity_base(view).
Functionality : This procedure returns all the data from the complete_entity_base view where the entity type id matches with the entity type id from the input.
';


--
-- Name: entitytype_attribute_add(attribute_base[], choice_attribute_detail_base[], attribute_fieldtype_option_value_base[], regex_base, related_entity_type_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entitytype_attribute_add(attributes_to_save attribute_base[], choice_attributes_to_save choice_attribute_detail_base[], attribute_fieldtype_option_values attribute_fieldtype_option_value_base[], regex_save regex_base, rel_entitytype_save related_entity_type_base) RETURNS SETOF complete_entity_type_base
    LANGUAGE plpgsql
    AS $$
DECLARE
    attribute_to_save client_template.attribute_base;
    choice_attribute_to_save client_template.choice_attribute_detail_base;
    attribute_fieldtype_option_value_save client_template.attribute_fieldtype_option_value_base;
    attribute_fieldtype_option_save client_template.attribute_fieldtype_option_base;
    related_entity_type_to_save  client_template.related_entity_type_base;
    aid bigint;
    cid bigint;
    fid bigint;
    fop bigint;
    fld_typ_id bigint;
    attribute_data_type bigint;
    complete_type client_template.complete_entity_type_base;
    choice_type_id bigint;
    fieldtype_attribute_id bigint;
    att_id bigint;
    reg_id bigint;
    usr character varying;
BEGIN
    
    FOR i IN COALESCE(array_lower(attributes_to_save,1),0) .. COALESCE(array_upper(attributes_to_save,1),-1) LOOP
        attribute_to_save:=attributes_to_save[i];
        IF attribute_to_save.mod_user IS NULL THEN
             RAISE EXCEPTION 'You cannot save without a mod username';
        END IF;
        IF attribute_to_save.attribute_id IS NULL or attribute_to_save.attribute_id = 0 THEN
            SELECT INTO attribute_to_save.attribute_id nextval('client_' || 'template.entity_attribute_b' || 'ase_entity_attribute_id_seq');
            fieldtype_attribute_id = attribute_to_save.attribute_id;
            IF attribute_to_save.attribute_fieldtype_id = 3 THEN
	        choice_type_id := attribute_to_save.attribute_id;
            END IF;
            INSERT INTO client_template.attribute_base VALUES(attribute_to_save.*)
            RETURNING * INTO attribute_to_save;      
        ELSE
            SELECT INTO aid attribute_id from client_template.attribute_base WHERE attribute_id = attribute_to_save.attribute_id;
            fieldtype_attribute_id = attribute_to_save.attribute_id;
            IF NOT FOUND THEN
                RAISE EXCEPTION 'One or more entities do not exist to update';
            ELSE
                SELECT INTO fid attribute_fieldtype_id from client_template.attribute_base WHERE attribute_id = attribute_to_save.attribute_id;
                IF fid <> attribute_to_save.attribute_fieldtype_id THEN
                   DELETE FROM ONLY client_template.attribute_fieldtype_option_value_base WHERE attribute_id = attribute_to_save.attribute_id ;
                END IF;                
                SELECT INTO regex_save.regex_id regex_id FROM ONLY client_template.related_fieldtype_regex_base WHERE fieldtype_id = attribute_to_save.attribute_fieldtype_id;                
                UPDATE client_template.attribute_base SET attribute_id=attribute_to_save.attribute_id, "order"=attribute_to_save.order, entity_type_id=attribute_to_save.entity_type_id, attribute_name=attribute_to_save.attribute_name, attribute_datatype_id=attribute_to_save.attribute_datatype_id, indexed=attribute_to_save.indexed, attribute_fieldtype_id=attribute_to_save.attribute_fieldtype_id, regex_id=regex_save.regex_id, required=attribute_to_save.required, mod_user=attribute_to_save.mod_user WHERE attribute_id=attribute_to_save.attribute_id;                
             
            END IF;
        END IF;
        att_id := attribute_to_save.attribute_id;
        usr := attribute_to_save.mod_user;
    END LOOP;
    
    FOR i IN COALESCE(array_lower(choice_attributes_to_save,1),0) .. COALESCE(array_upper(choice_attributes_to_save,1),-1) LOOP
        choice_attribute_to_save := choice_attributes_to_save[i];        
        IF choice_attribute_to_save.mod_user IS NULL THEN
             RAISE EXCEPTION 'You cannot save without a mod username';
        END IF;
        IF choice_attribute_to_save.display_type IS NULL THEN
             RAISE EXCEPTION 'Plz enter Display type';
        END IF;

        IF choice_attribute_to_save.attribute_id IS NULL or choice_attribute_to_save.attribute_id = 0  THEN
            choice_attribute_to_save.attribute_id := choice_type_id;
        END IF;
        IF choice_attribute_to_save.choice_attribute_id IS NULL or choice_attribute_to_save.choice_attribute_id = 0 THEN
            SELECT INTO choice_attribute_to_save.choice_attribute_id nextval('client_' || 'template.choice_attribute_detail_seq_id');
            INSERT INTO client_template.choice_attribute_detail_base VALUES(choice_attribute_to_save.*)
            RETURNING * INTO choice_attribute_to_save;
        ELSE
                SELECT INTO cid choice_attribute_id from client_template.choice_attribute_detail_base WHERE choice_attribute_id = choice_attribute_to_save.choice_attribute_id;
            IF NOT FOUND THEN
                RAISE EXCEPTION 'One or more entities do not exist to update';
            ELSE
		UPDATE client_template.choice_attribute_detail_base SET choice_attribute_id=choice_attribute_to_save.choice_attribute_id, attribute_id=choice_attribute_to_save.attribute_id, display_type=choice_attribute_to_sav.display_typee, choice_entity_type_attribute_id=choice_attribute_to_save.choice_entity_type_attribute_id, mod_user=choice_attribute_to_save.mod_user WHERE choice_attribute_id=choice_attribute_to_save.choice_attribute_id;
            END IF;
        END IF;
    END LOOP;
    
    -- FieldType Option Values Save
    FOR i IN COALESCE(array_lower(attribute_fieldtype_option_values,1),0) .. COALESCE(array_upper(attribute_fieldtype_option_values,1),-1) LOOP
        attribute_fieldtype_option_value_save := attribute_fieldtype_option_values[i];
        IF attribute_fieldtype_option_value_save.option_value_id IS NULL or attribute_fieldtype_option_value_save.option_value_id = 0  THEN
		SELECT INTO attribute_fieldtype_option_value_save.option_value_id nextval('client_' || 'template.attribute_fieldtype_option_value_seq');
		attribute_fieldtype_option_value_save.attribute_id:=fieldtype_attribute_id;
		INSERT INTO client_template.attribute_fieldtype_option_value_base VALUES(attribute_fieldtype_option_value_save.*);
	ELSE
	        SELECT INTO fop option_value_id FROM ONLY client_template.attribute_fieldtype_option_value_base WHERE option_value_id = attribute_fieldtype_option_value_save.option_value_id;
	        IF NOT FOUND THEN
	            
	        ELSE  
	 	    UPDATE client_template.attribute_fieldtype_option_value_base SET option_value_id=attribute_fieldtype_option_value_save.option_value_id, attribute_id=attribute_fieldtype_option_value_save.attribute_id, attribute_fieldtype_option_id=attribute_fieldtype_option_value_save.attribute_fieldtype_option_id, value=attribute_fieldtype_option_value_save.value WHERE option_value_id=attribute_fieldtype_option_value_save.option_value_id;
                END IF;
        END IF;
    END LOOP;

    --Regex save
    IF (regex_save IS NULL) OR (regex_save.regex_id =0) THEN 
        SELECT INTO fld_typ_id attribute_fieldtype_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_id AND mod_user = usr;
        IF NOT FOUND THEN
        ELSE
            SELECT INTO regex_save.regex_id regex_id FROM ONLY client_template.related_fieldtype_regex_base WHERE fieldtype_id = fld_typ_id;
            UPDATE client_template.attribute_base SET regex_id=regex_save.regex_id WHERE attribute_id=att_id;                
        END IF;   
    ELSE
       IF regex_save.custom IS TRUE THEN
          IF (regex_save.regex_id IS NULL) OR (regex_save.regex_id = 0) THEN
              SELECT INTO regex_save.regex_id nextval('client_' || 'template.regex_id_seq');
              INSERT INTO client_template.regex_base VALUES(regex_save.*)
              RETURNING * INTO regex_save;
              UPDATE client_template.attribute_base SET regex_id = regex_save.regex_id WHERE attribute_id = att_id AND mod_user = usr;
          ELSE
              SELECT INTO reg_id regex_id FROM ONLY client_template.regex_base WHERE regex_id = regex_save.regex_id;
              IF NOT FOUND THEN
                 RAISE EXCEPTION 'Regex Does not Exist';
              ELSE
                 UPDATE client_template.regex_base SET pattern= regex_save.pattern, display_name=regex_save.display_name, custom=regex_save.custom WHERE regex_id = regex_save.regex_id;
              END IF;
          END IF;
       ELSE 
             UPDATE client_template.attribute_base SET regex_id = regex_save.regex_id WHERE attribute_id = att_id AND mod_user = usr;
       END IF;      
    END IF;
    --Related entity type save
    IF (rel_entitytype_save IS NULL) OR (rel_entitytype_save.child_entity_type_id = 0) THEN    
    ELSE
       IF (rel_entitytype_save.related_entity_type_id IS NULL) OR (rel_entitytype_save.related_entity_type_id = 0) THEN
           SELECT INTO rel_entitytype_save.related_entity_type_id nextval('client_' || 'template.related_entity_type_id_seq');
           rel_entitytype_save.attribute_id = fieldtype_attribute_id;
           INSERT INTO client_template.related_entity_type_base VALUES(rel_entitytype_save.*);
       ELSE
           rel_entitytype_save.attribute_id = fieldtype_attribute_id;
           UPDATE client_template.related_entity_type_base SET related_entity_type_id = rel_entitytype_save.related_entity_type_id, attribute_id = rel_entitytype_save.attribute_id, child_entity_type_id = rel_entitytype_save.child_entity_type_id, collapse = rel_entitytype_save.collapse, mod_user = rel_entitytype_save.mod_user;
       END IF;
 
    END IF;
    
    FOR complete_type IN SELECT * FROM ONLY client_template.complete_entity_type_base LOOP
        RETURN NEXT complete_type;
    END LOOP;
END;$$;


--
-- Name: FUNCTION entitytype_attribute_add(attributes_to_save attribute_base[], choice_attributes_to_save choice_attribute_detail_base[], attribute_fieldtype_option_values attribute_fieldtype_option_value_base[], regex_save regex_base, rel_entitytype_save related_entity_type_base); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entitytype_attribute_add(attributes_to_save attribute_base[], choice_attributes_to_save choice_attribute_detail_base[], attribute_fieldtype_option_values attribute_fieldtype_option_value_base[], regex_save regex_base, rel_entitytype_save related_entity_type_base) IS 'Input :  attribute base table(array of objects),choice_attribute_detail_base(array of objects),attribute_fieldtype_option_value_base(array of objects),regex_base(object),related_entity_type_base(object).

Return Type : complete_entity_type_base(View).

functionality : This procedure adds attributes to the entity type.if the attribute is of filed type choice we add corresponding data in the  choice_attribute_detail_base table.If the attribute is of the field type related entity then we add corresponding data in the related_entity_type_base table.

Steps Involed:
     1) We craete new attributes with unique attribute ids by generating sequence from the entity_attribute_base_entity_attribute_id_seq table.If the attribute is already created will update the corresponding attribute with attribute id from the input.
     2) If the attribute field type is choice then we create a row in the choice_attribute_detail_base table with the attribute id and field type id as foreign keys.
     3) If the attribute field type is related entity type  then we create a row in the related_entity_type_base table with the attribute id and parent entity type id as foreign keys.
     4) We check if the user has assigned a regex for the attribute , if not then we will assign the default regex associted with filed type assigned to the attribute fro this we use the related_fieldtype_regex_base table.
     5) Ater saving all the attributes we return the complete_entity_type_base view.';


--
-- Name: entitytype_copy(bigint, character varying, integer, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entitytype_copy(entity_type_id_to_save bigint, new_entity_type_name character varying, option integer, username character varying) RETURNS SETOF attribute_base
    LANGUAGE plpgsql
    AS $$
DECLARE
     eid client_template.entity_type_base;
     atid integer;
     newdata client_template.attribute_base;
     olddata client_template.attribute_base;
     data_to_copy client_template.attribute_value_storage_base;
	
BEGIN

      IF username IS NULL THEN
	    RAISE EXCEPTION 'You should have username';
      END IF;
	
      IF option = 1 OR option = 2 THEN
      ELSE 
            RAISE EXCEPTION 'The values for option must be either 1 or 2';
      END IF;

      IF option = 1 THEN

	SELECT INTO eid.entity_type_id nextval('client_' || 'template.related_entity_type_id_seq');
	eid.entity_type_name := new_entity_type_name;
	eid.mod_user := username;
	INSERT INTO client_template.entity_type_base VALUES(eid.*);
	select * INTO newdata from client_template.attribute_base where entity_type_id = entity_type_id_to_save;
        FOR newdata IN SELECT * FROM client_template.attribute_base where entity_type_id = entity_type_id_to_save ORDER BY attribute_id LOOP
             atid :=0;
             newdata.entity_type_id := eid.entity_type_id;
             SELECT INTO newdata.attribute_id nextval('client_' || 'template.entity_attribute_' || 'base_entity_attribute_id_seq'); 
             INSERT INTO client_template.attribute_base VALUES(newdata.*);
        END LOOP;
      END IF; 
      
      IF option = 2 THEN
        SELECT INTO eid.entity_type_id nextval('client_' || 'template.related_entity_type_id_seq');
	eid.entity_type_name := new_entity_type_name;
	eid.mod_user := username;
	INSERT INTO client_template.entity_type_base VALUES(eid.*);
	select * INTO newdata from client_template.attribute_base where entity_type_id = entity_type_id_to_save;
        FOR newdata IN SELECT * FROM client_template.attribute_base where entity_type_id = entity_type_id_to_save ORDER BY attribute_id LOOP
               atid :=0;
               olddata := newdata;
               newdata.entity_type_id := eid.entity_type_id;
               SELECT INTO newdata.attribute_id nextval('client_' || 'template.entity_attribute_' || 'base_entity_attribute_id_seq'); 
               INSERT INTO client_template.attribute_base VALUES(newdata.*);
              
               FOR data_to_copy IN SELECT * FROM client_template.attribute_value_storage_base where attribute_id =  olddata.attribute_id ORDER BY attribute_value_id LOOP
                    SELECT INTO data_to_copy.attribute_value_id nextval('client_' || 'template.attribute_value_' || 'base_id_seq');
                    data_to_copy.attribute_id := newdata.attribute_id;
                    INSERT INTO client_template.attribute_value_storage_base VALUES(data_to_copy.*);
               END LOOP;     
        END LOOP;
      END IF;
	
      FOR newdata IN SELECT * FROM ONLY client_template.attribute_base LOOP
	  RETURN NEXT newdata;
      END LOOP;
	
END;$$;


--
-- Name: FUNCTION entitytype_copy(entity_type_id_to_save bigint, new_entity_type_name character varying, option integer, username character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entitytype_copy(entity_type_id_to_save bigint, new_entity_type_name character varying, option integer, username character varying) IS 'Input : entity type id(Original entity id to copy),entity type name (name of the duplicated entity type),option(copy only entity type structure or copy with data),username.
Return Type : attribute_base table.
Fuctionality : This procedure creates an instance of an existing entity type.We input the entity type id which we want to duplicate and the name for the duplicated entity type.We also take the option form the input whether to duplicate only the structure of the entity type or data alaong with structure.
Steps Involved:
   1) We check if the input for option is 1,that means we copy only the structure of the entity type.
        We get the new entity type id from the sequence table  related_entity_type_id_seq.
        We create the entity type with newly created entity type id and the entity type name from the input.
        We get the attributes from the original entity type and duplicate the same attributes with different 
        attributes id and save them with the new entity type id.
   2) If the iput is 2 then we will copy the data along with structure.
       We follow the steps involved in step 1 to copy the structure and additionally we copy the data from the        attribute_value_storage_base with the original entity type id and save them with the newly created 
       entity type .
   3) Finally we return the attribute base table.';


--
-- Name: entitytype_load(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entitytype_load() RETURNS SETOF complete_entity_type_base
    LANGUAGE sql
    AS $$SELECT * FROM client_template.complete_entity_type_base$$;


--
-- Name: FUNCTION entitytype_load(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entitytype_load() IS 'Input : None.
Return Type : complete_entity_type_base(view).
Functinality : This function loads the entire  complete_entity_type_base view.';


--
-- Name: entitytype_tab_load(character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION entitytype_tab_load(user_name character varying, num_lmt bigint, num_ofset bigint) RETURNS SETOF entity_type_base
    LANGUAGE plpgsql
    AS $$DECLARE  
   entitytype client_template.entity_type_base;  
BEGIN
   IF user_name = NULL THEN
       RAISE EXCEPTION 'YOU MUST PASS USER NAME';
   END IF;     
   FOR entitytype IN SELECT entity_type_id,entity_type_name,(SELECT count(entity_type_id) AS count FROM client_template.entity_type_base) FROM ONLY client_template.entity_type_base where mod_user = user_name LIMIT num_lmt OFFSET num_ofset LOOP            
         RETURN NEXT entitytype ;
   END LOOP;
END;$$;


--
-- Name: FUNCTION entitytype_tab_load(user_name character varying, num_lmt bigint, num_ofset bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION entitytype_tab_load(user_name character varying, num_lmt bigint, num_ofset bigint) IS 'Input : User Name , limit,offset.
Retrurn Type : entity_type_base table.
Functiinality : This procedure returns all the rows from the entity_type_base table that matches with the user name from the input with limit and offset for pagination purpose.';


--
-- Name: import_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE import_base (
    import_id character varying NOT NULL,
    number_attempted bigint,
    number_failed bigint,
    time_start timestamp with time zone,
    time_ended timestamp with time zone,
    completed boolean DEFAULT false
);


--
-- Name: import_fetch(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION import_fetch(universal_id character varying) RETURNS SETOF import_base
    LANGUAGE plpgsql
    AS $$BEGIN
       RETURN QUERY SELECT * FROM client_template.import_base WHERE  import_id = universal_id;
END;$$;


--
-- Name: internal_entity_search(text); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search(text_to_convert text) RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
    text_converted text;
BEGIN

    text_converted := lower(text_to_convert);
    text_converted := replace(text_converted, '& and &', '&');
    text_converted := replace(text_converted, '& not &', '&');
    text_converted := replace(text_converted, '& or &', '&');    
    text_converted := replace(text_converted, 'and:*', '&');
    text_converted := replace(text_converted, '| & |', '&');
    text_converted := replace(text_converted, 'not:*', '&!');
    text_converted := replace(text_converted, '| &! |', '&!');
    text_converted := replace(text_converted, '& &! |', '&!');
    text_converted := replace(text_converted, 'or:*', '|');
    text_converted := replace(text_converted, '| | |', '|');
    text_converted := replace(text_converted, '& &!', '&!');
    text_converted := replace(text_converted, '& (:* |', '& (');
    text_converted := replace(text_converted, '| (:* |', '| (');
    text_converted := replace(text_converted, '| ):*', ')');
    text_converted := replace(text_converted, '):*', ':* )');
    text_converted := replace(text_converted, '| &!:* |', '&!');
    text_converted := replace(text_converted, '| |:* |', '|');
    text_converted := replace(text_converted, '| &:* |', '&');

    RETURN text_converted;

END;$$;


--
-- Name: FUNCTION internal_entity_search(text_to_convert text); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION internal_entity_search(text_to_convert text) IS 'Input : text
Return Type : text
Functionality : This procedure is an internal  function to the simple and complex searches.Here we convert the search data into the format that the postgres can understand.It is here where we build the search terms with operators.
Ex: Jason and tesser   =>  Jason & tesser
      Jason or tesser      =>  Jason | tesser 

';


--
-- Name: internal_entity_search_complex_getcnt(bigint[], character varying[], character varying[], bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_complex_getcnt(att_ids bigint[], data_arr character varying[], optrs character varying[], enttype_id bigint) RETURNS bigint
    LANGUAGE plpgsql
    AS $$DECLARE
    k int;
    bs_ent_ids bigint[];
    bs_ent_id bigint;
    bs_rs_count bigint;
    tmp_ent_ids bigint[];
    tmp_ent_id bigint;
    rs_count bigint;
    inc bigint;
    att_tbl client_template.attribute_base;
    rs_cnt bigint;
BEGIN
              bs_ent_ids := NULL;
              inc := 1;
              FOR i IN COALESCE(array_lower(att_ids,1),0) .. COALESCE(array_upper(att_ids,1),-1) LOOP
                  IF enttype_id = 0 OR enttype_id IS NULL THEN
                     SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = att_ids[i];
                  ELSE
                     SELECT * INTO att_tbl from only client_template.attribute_base where attribute_id = att_ids[i] AND entity_type_id = enttype_id;
                  END IF;
                  IF NOT FOUND THEN
                  ELSE
                     IF bs_ent_id IS NULL THEN
                        k:=1;
                        FOR bs_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(data_arr[i]) AND attribute_id = att_ids[i] LOOP
                            bs_ent_ids[k] := bs_ent_id;
                            k := k+1;
                        END LOOP;
                        IF array_length(bs_ent_ids,1) > 0 THEN
                        ELSE
                           IF (COALESCE(optrs[i]) LIKE 'and') OR (COALESCE(optrs[i]) LIKE 'not') THEN
                               RAISE EXCEPTION 'exit ';
                           END IF;        
                        END IF;
                     ELSE 
                         k:=1;
                         FOR tmp_ent_id IN SELECT entity_id FROM ONLY client_template.attribute_value_storage_base atvs WHERE search_index @@ to_tsquery(data_arr[i]) AND attribute_id = att_ids[i] LOOP
                            tmp_ent_ids[k] := tmp_ent_id;
                            k := k+1;
                         END LOOP;
                         IF array_length(tmp_ent_ids,1) > 0 THEN
                            IF (COALESCE(optrs[i-1]) LIKE 'and') THEN
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) INTERSECT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(optrs[i-1]) LIKE 'not') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) EXCEPT SELECT UNNEST(tmp_ent_ids)));
                            ELSIF (COALESCE(optrs[i-1]) LIKE 'or') THEN 
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                            ELSE
                               bs_ent_ids := (SELECT ARRAY(SELECT UNNEST(bs_ent_ids) UNION SELECT UNNEST(tmp_ent_ids)));
                         END IF;     
                     END IF;
                  END IF;
                END IF;
              END LOOP;
              rs_cnt := array_length(bs_ent_ids,1);
              RETURN rs_cnt;
END;$$;


--
-- Name: internal_entity_search_complex_mod(character varying[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_complex_mod(search_terms character varying[]) RETURNS public.complex_entity_search_type
    LANGUAGE plpgsql
    AS $$DECLARE
    search_data text;
    text_result text;
    search_entitytype character varying;
    search_entitytype_names character varying[];
    rtn text;
    sam text;
    cnt bigint;
    ent_typ_ids bigint[];
    ent_typ_id bigint;
    ct1_search_data text;
    ct1_search_attribute character varying[];
    ct1_att_id bigint;
    ct1_att_ids bigint[];
    ct2_search_temp character varying[];
    ct2_search_data text;
    ct2_search_data_arr character varying[];
    ct2_data character varying[];
    ct2_search_attribute character varying;
    ct2_att_id bigint;
    ct2_att_ids bigint[];
    ct2_search_dat character varying[];
    ct3_search_temp character varying[];
    ct3_search_data text;
    ct3_data character varying[];
    ct3_search_attribute character varying;
    ct3_att_ids bigint[];
    ct3_search_dat character varying[];
    ct3_search_data_arr character varying[];
    ct3_att_id bigint;
    ct4_search_temp character varying[];
    ct4_search_data character varying;
    c4_search_data character varying;
    ct4_att_id bigint;
    c4_search_att_data character varying;
    c4_data character varying;
    ct1_rs_count bigint;
    ct2_rs_count bigint;
    ct3_rs_count bigint;
    ct4_rs_count bigint;
    gn_rs_count bigint;
    ent_value client_template.entity_base;
    att_value_temp client_template.attribute_value_storage_base;
    att_id bigint[];
    temp character varying;
    gn_search_data character varying;
    att_values client_template.attribute_value_storage_base;
    att_value client_template.attribute_value_storage_base;
    avs client_template.attribute_value_storage_base[];
    result_data public.complete_entity_search_count;
    att_tbl client_template.attribute_base;
    start_tag character varying;
    end_tag character varying;
    highlight_pattern character varying;
    en_id bigint;
    m bigint; 
    etype_id bigint;
    oprt character varying[];
    oprts character varying[];
    cntl bigint;
    att_strg_val client_template.attribute_value_storage_base;
    enty_ids client_template.entity_base;
    search_ids_and_data public.complex_entity_search_type;
BEGIN
    search_data :='' ;
    cntl := 1;
    FOR i IN COALESCE(array_lower(search_terms,1),0) .. COALESCE(array_upper(search_terms,1),-1) LOOP
       text_result := search_terms[i];
       text_result := lower(text_result);
       IF ( text_result LIKE 'type:[%]') THEN
            search_entitytype := replace(text_result , 'type:', '');
            search_entitytype := replace(search_entitytype , '[', '');
            search_entitytype := replace(search_entitytype , ']', '');
            search_entitytype_names = regexp_split_to_array(search_entitytype, ' or ');
            FOR i IN COALESCE(array_lower(search_entitytype_names,1),0) .. COALESCE(array_upper(search_entitytype_names,1),-1) LOOP
                SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.entity_type_base WHERE lower(entity_type_name) = search_entitytype_names[i];
                IF NOT FOUND THEN
                   RAISE EXCEPTION ' Entity Type Does Not Exist';  
                ELSE  
                   ent_typ_ids := array_append(ent_typ_ids, ent_typ_id);
                   search_ids_and_data.entity_type_ids := ent_typ_ids;
                END IF;    
            END LOOP;
            
       ELSIF (text_result LIKE 'type:%') THEN
            search_entitytype := replace(text_result , 'type:', '');
            SELECT INTO ent_typ_id entity_type_id FROM ONLY client_template.entity_type_base WHERE lower(entity_type_name) = search_entitytype;
            IF NOT FOUND THEN
                   RAISE EXCEPTION ' Entity Type Does Not Exist';  
            ELSE  
                   ent_typ_ids := array_append(ent_typ_ids, ent_typ_id);
                   search_ids_and_data.entity_type_ids := ent_typ_ids;
            END IF; 
            
       ELSIF (text_result LIKE 'or') OR (text_result LIKE 'and') OR (text_result LIKE 'not')THEN
             oprt[cntl] := text_result;
             oprts := array_append(oprts, oprt[cntl]);
             search_ids_and_data.ct2_oprts := oprts;
             cntl := cntl+1;     
       ELSIF (text_result LIKE '[%:%]') THEN
            ct1_search_data := replace(text_result , '[', '');
            ct1_search_data := replace(ct1_search_data , ']', '');
            ct1_search_attribute = regexp_split_to_array(ct1_search_data , ':');
            FOR i IN COALESCE(array_lower(ct1_search_attribute ,1),0) .. COALESCE(array_upper(ct1_search_attribute ,1),-1) LOOP
                    temp := ct1_search_attribute [i];
                    temp := trim(both ' ' from temp);
                    SELECT INTO ct1_att_id attribute_id FROM ONLY client_template.attribute_base WHERE lower(attribute_name) =temp;
                    IF NOT FOUND THEN
                        ct1_search_data  := temp ||':*';
                        search_ids_and_data.ct1_sdata := ct1_search_data;
                    ELSE
                        ct1_att_ids := array_append(ct1_att_ids, ct1_att_id);
                        search_ids_and_data.ct1_attids := ct1_att_ids;   
                    END IF;
            END LOOP;
            
       ELSIF (text_result LIKE '[% or %]%:%') OR (text_result LIKE '[% and %]%:%') OR (text_result LIKE '[% not %]%:%')THEN
           ct2_search_temp := regexp_split_to_array(text_result, ':');
           ct2_search_data := trim(both ' ' from ct2_search_temp[1]);
           ct2_search_dat[1] := client_template.internal_entity_search_complex_mod2(ct2_search_data);
           ct2_search_data_arr := array_append(ct2_search_data_arr, ct2_search_dat[1]); 
           search_ids_and_data.ct2_sdata := ct2_search_data_arr;
           ct2_search_temp := regexp_split_to_array(text_result, ':');
           ct2_search_attribute := trim(both ' ' from ct2_search_temp[2]);
           SELECT INTO ct2_att_id attribute_id FROM ONLY client_template.attribute_base WHERE lower(attribute_name) =  ct2_search_attribute;
           IF NOT FOUND THEN
                RAISE EXCEPTION 'Attribute does not exist';  
           ELSE
                ct2_att_ids := array_append(ct2_att_ids, ct2_att_id);
                search_ids_and_data.ct2_attids := ct2_att_ids;  
           END IF;
       ELSIF (text_result LIKE '%:%[% or %]') OR (text_result LIKE '%:%[% and %]') OR (text_result LIKE '%:%[% not %]') OR (text_result LIKE '%:%[%]')THEN
           m := 0;
           ct3_search_temp := regexp_split_to_array(text_result, ':');
           ct3_search_data := trim(both ' ' from ct3_search_temp [2]);
           ct3_search_dat[1] := client_template.internal_entity_search_complex_mod2(ct3_search_data);
           --ct3_search_data_arr := array_append(ct3_search_data_arr, ct3_search_dat[1]);
           --search_ids_and_data.ct3_sdata := ct3_search_data_arr;
           ct3_search_temp := regexp_split_to_array(text_result, ':');
           ct3_search_attribute := trim(both ' ' from ct3_search_temp [1]);
           SELECT INTO ct3_att_id attribute_id FROM ONLY client_template.attribute_base WHERE lower(attribute_name) =  ct3_search_attribute;
           IF NOT FOUND THEN
               RAISE EXCEPTION 'Attribute does not exist';  
           ELSE
               FOR ct3_att_id IN SELECT attribute_id FROM ONLY client_template.attribute_base WHERE lower(attribute_name) =  ct3_search_attribute loop
                   ct3_att_ids := array_append(ct3_att_ids, ct3_att_id);
                   ct3_search_data_arr := array_append(ct3_search_data_arr, ct3_search_dat[1]);
                   search_ids_and_data.ct3_attids := ct3_att_ids;
                   m := m+1; 
               END LOOP;
               search_ids_and_data.ct3_sdata := ct3_search_data_arr;
           END IF;
           IF m > 1 THEN
              oprt[cntl] := 'or';
              oprts := array_append(oprts, oprt[cntl]);
              cntl := cntl+1;
              m := 0;
           END IF;
       ELSIF (text_result LIKE '[%:%]%and%') OR (text_result LIKE '[%:%]%or%') OR (text_result LIKE '[%:%]% not %')THEN
           c4_data := text_result;
            IF (c4_data LIKE '[%:%]%and%') THEN
               ct4_search_temp := regexp_split_to_array(text_result, ' and ');
            ELSIF (c4_data LIKE '[%:%]%or%') THEN
               ct4_search_temp := regexp_split_to_array(text_result, ' or ');
            ELSIF (c4_data LIKE '[%:%]%not%') THEN
              ct4_search_temp := regexp_split_to_array(text_result, ' not ');
           ELSE
           END IF;     
           ct4_search_data := trim(both ' ' from ct4_search_temp [2]);
           text_result := ct4_search_temp[1];
           c4_search_data := replace(text_result , '[', '');
           c4_search_data := replace(c4_search_data , ']', '');
           ct4_search_temp := regexp_split_to_array(c4_search_data, ':');
           FOR i IN COALESCE(array_lower(ct4_search_temp ,1),0) .. COALESCE(array_upper(ct4_search_temp ,1),-1) LOOP
                    temp := ct4_search_temp [i];
                    temp := trim(both ' ' from temp);
                   
                    SELECT INTO ct4_att_id attribute_id FROM client_template.attribute_base WHERE lower(attribute_name) =temp;
                    IF NOT FOUND THEN
                        c4_search_att_data := temp||':*';
                        search_ids_and_data.ct4_attdata := c4_search_att_data;
                        IF (c4_data LIKE '[%:%]%and%') THEN
                            c4_search_data  := temp||':*' || ' & ' || ct4_search_data||':*';
                            search_ids_and_data.ct4_sdata := c4_search_data;
                        ELSIF (c4_data LIKE '[%:%]%or%') THEN
                            c4_search_data  := temp||':*' || ' | ' || ct4_search_data||':*';
                            search_ids_and_data.ct4_sdata := c4_search_data;
                        ELSIF (c4_data LIKE '[%:%]%not%') THEN
                            c4_search_data  := temp||':*' || ' &! ' || ct4_search_data||':*';
                            search_ids_and_data.ct4_sdata := c4_search_data;
                        ELSE
                        END IF;     
                    ELSE
                         FOR ct4_att_id IN SELECT attribute_id FROM client_template.attribute_base WHERE lower(attribute_name) =temp LOOP
                         att_id := array_append(att_id, ct4_att_id);
                         search_ids_and_data.ct3_oprts := att_id;
                         END LOOP;
                    END IF;
            END LOOP;
       ELSE
            gn_search_data := client_template.internal_entity_search_complex_mod2(text_result);
            search_ids_and_data.gn_sdata := gn_search_data;
       END IF;    
    END LOOP;
    RETURN search_ids_and_data;
END;$$;


--
-- Name: FUNCTION internal_entity_search_complex_mod(search_terms character varying[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION internal_entity_search_complex_mod(search_terms character varying[]) IS 'Input : Array of search terms

Return Type : complex_entity_search_type(custom type)

Functionality : This function is an internal funtion to complex_entity_search().The purpose of this function is to seperate the entity types, attributes and actual search terms from the sequence of strings entered by the user in the search query box.We identify the each search terms with one of the standard patterns that the user might use for comple query.

Steps Involved :
     1) We loop over the array of search terms and compare the structure of the search terms with the standard patterns we created.Each search terms will match with one of the category. 
     2) We split the search term into individual words and identify the whether it is entity type or attribute or search data.
     3) With each iteration we build the arrays of entiy type ids,attribute ids and the search terms into seperate arrays 
     4) When the iteration are over we pass the info to the complex search function which builds the dynamic queries depending on the data we provided here.
';


--
-- Name: internal_entity_search_complex_mod1(attribute_value_storage_base, character varying, character varying, bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_complex_mod1(att_vals attribute_value_storage_base, oprts character varying, search_data character varying, att_id bigint, ent_id bigint) RETURNS attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$
DECLARE
    
     att_val client_template.attribute_value_storage_base;
     search_data text;
     oprt character varying;
     att_strg_val client_template.attribute_value_storage_base;
BEGIN
     att_val := att_vals;
     
     IF (COALESCE(oprt) LIKE 'or') THEN
     
     ELSIF (COALESCE(oprt) LIKE 'and') THEN
        RAISE EXCEPTION 'DATA';
        IF search_data <> NULL THEN
             SELECT * INTO att_strg_val FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(search_data) and attribute_id = att_id and entity_id = ent_id;
             IF NOT FOUND THEN
                SELECT INTO att_val null;
             ELSE
             END IF; 
        END IF;     
     ELSIF (COALESCE(oprt) LIKE 'not') THEN
        RAISE EXCEPTION 'DATA';
        IF search_data <> NULL THEN
             SELECT * INTO att_strg_val FROM ONLY client_template.attribute_value_storage_base WHERE search_index @@ to_tsquery(search_data) and attribute_id = att_id and entity_id = ent_id;
             IF NOT FOUND THEN
             ELSE
                SELECT INTO att_val null;
             END IF; 
        END IF;     
     ELSE      
     END IF; 
     RETURN att_val;
END;$$;


--
-- Name: internal_entity_search_complex_mod2(text); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_complex_mod2(search_term text) RETURNS text
    LANGUAGE plpgsql
    AS $$DECLARE
    search_data text;
    text_result text;
    ct_data character varying[];
    search_data_arr character varying[];

BEGIN
           search_term := replace(search_term , '[', '');
           search_term := replace(search_term , ']', '');
           search_data_arr := regexp_split_to_array(search_term,' ');
           search_data := '';
           FOR i IN COALESCE(array_lower(search_data_arr,1),0) .. COALESCE(array_upper(search_data_arr,1),-1) LOOP
               IF (search_data_arr[i] LIKE 'or')  THEN
                   search_data_arr[i] := replace(search_data_arr[i] , 'or', '|');
               ELSIF (search_data_arr[i] LIKE 'and')  THEN
                   search_data_arr[i] := replace(search_data_arr[i] , 'and', '&');
               ELSIF (search_data_arr[i] LIKE 'not')  THEN   
                   search_data_arr[i] := replace(search_data_arr[i] , 'not', '&!');
               ELSE
                   search_data_arr[i] := trim(both ' ' from search_data_arr[i]);
                   search_data_arr[i] := search_data_arr[i] || ':*' ;
               END IF;        
           END LOOP;
           FOR i IN COALESCE(array_lower(search_data_arr,1),0) .. COALESCE(array_upper(search_data_arr,1),-1) LOOP
                search_data := search_data ||' '||search_data_arr[i];
           END LOOP;
           search_data := replace(search_data, '(:*', '(');
           search_data := replace(search_data, '):*', ')');
           RETURN search_data;
END;$$;


--
-- Name: FUNCTION internal_entity_search_complex_mod2(search_term text); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION internal_entity_search_complex_mod2(search_term text) IS 'Input : search term (text)
Return Type : search term (text)
Funtinality : This function is an internal funtion of the search prcedures, inthis function the actual search term is prepared. Here all the repalcements will be done.

Ex : search term = '' ram and ravi''
        converted to '' ram & ravi''                     ';


--
-- Name: internal_entity_search_getcnt(bigint[]); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_getcnt(result_cnt bigint[]) RETURNS bigint
    LANGUAGE plpgsql
    AS $$DECLARE
rs_cnt bigint[];
temp bigint;
inc bigint;
BEGIN
     inc:= 1;
     FOR j IN  COALESCE(array_lower(result_cnt,1),0) .. COALESCE(array_upper(result_cnt,1),-1) LOOP
         FOR i IN COALESCE(array_lower(result_cnt,1),0) .. COALESCE(array_upper(result_cnt,1),-1) LOOP
             IF (result_cnt[i] > result_cnt[i+1]) THEN
                temp := result_cnt[i];
                result_cnt[i] := result_cnt[i+1];
                result_cnt[i+1] := temp;
             END IF;
         END LOOP;
     END LOOP; 
     FOR i IN COALESCE(array_lower(result_cnt,1),0) .. COALESCE(array_upper(result_cnt,1),-1) LOOP
         IF (result_cnt[i] = result_cnt[i+1]) THEN
         ELSE 
            rs_cnt[inc] := result_cnt[i];
            inc := inc+1;
         END IF;
    END LOOP; 

    RETURN array_length(rs_cnt,1);

END;$$;


--
-- Name: FUNCTION internal_entity_search_getcnt(result_cnt bigint[]); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION internal_entity_search_getcnt(result_cnt bigint[]) IS 'Input : 
Return Type :
Functionality : ';


--
-- Name: internal_entity_search_highlighting(attribute_value_storage_base, text, character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_search_highlighting(att_val attribute_value_storage_base, data_to_search text, pattern character varying) RETURNS attribute_value_storage_base
    LANGUAGE plpgsql
    AS $$
DECLARE
text_result text;
BEGIN

      IF length(att_val.value_varchar)> 0 THEN 
            text_result := att_val.value_varchar;
      ELSIF att_val.value_long != 0 THEN
            text_result := att_val.value_long; 
      ELSIF att_val.value_date != NULL THEN
            text_result := att_val.value_date;
      ELSIF length(att_val.value_text)> 0 THEN  
            text_result := att_val.value_text; 
      ELSE
      END IF;
      text_result := ts_headline('english',text_result ,to_tsquery(data_to_search), pattern);
      IF length(att_val.value_varchar)> 0 THEN 
            att_val.value_varchar := text_result;
      ELSIF att_val.value_long != 0 THEN
            --att_val.value_long := text_result; 
      ELSIF att_val.value_date != NULL THEN
            att_val.value_date := text_result;
      ELSIF length(att_val.value_text)> 0 THEN  
            att_val.value_text := text_result; 
      ELSE
      END IF;


      RETURN att_val;
END;$$;


--
-- Name: FUNCTION internal_entity_search_highlighting(att_val attribute_value_storage_base, data_to_search text, pattern character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION internal_entity_search_highlighting(att_val attribute_value_storage_base, data_to_search text, pattern character varying) IS 'Input : Object of attribute_value_storage_base, search data, pattern to hilight.
Return Type: attribute_value_storage_base (with highlighted search data).
Functionality :  This procedure is an internal funtion used in the searches for highlighting the searched word in the content.

Steps involved: Takes the  attribute_value_storage_base  data row and identify the which column has the data from (value_varchar,value_date,value_long and value_text). Then the data along with highlighting patter is sent to the prostgres function ts_headline() which highlights the search data with pattern provided . 
';


--
-- Name: internal_entity_value_to_set(bulk_entity_base); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION internal_entity_value_to_set(ent_storage_value_rec bulk_entity_base) RETURNS SETOF bulk_entity_base
    LANGUAGE plpgsql
    AS $$
DECLARE
      entity client_template.entity_base;
      attribute_value_storage_rec client_template.attribute_value_storage_base;
    
BEGIN
      entity.entity_id := ent_storage_value_rec.entity_id;
      entity.entity_type_id := ent_storage_value_rec.entity_type_id;
      entity.mod_user := ent_storage_value_rec.mod_user;
      entity.search_index := ent_storage_value_rec.search_index;
      attribute_value_storage_rec.attribute_value_id := ent_storage_value_rec.attribute_value_id ;
      attribute_value_storage_rec.attribute_id :=  ent_storage_value_rec.attribute_id;
      attribute_value_storage_rec.entity_id := ent_storage_value_rec.entity_id;
      attribute_value_storage_rec.value_varchar := ent_storage_value_rec.value_varchar;
      attribute_value_storage_rec.value_long := ent_storage_value_rec.value_long;
      attribute_value_storage_rec.value_date := ent_storage_value_rec.value_date ;
      attribute_value_storage_rec.value_text := ent_storage_value_rec.value_text;
      attribute_value_storage_rec.mod_user := ent_storage_value_rec.mod_user ;
             
END;$$;


--
-- Name: maping_fieldtypes_to_datatypes(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION maping_fieldtypes_to_datatypes() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
   dt_vachr bigint[];
   dt_lng  bigint[];
   dt_dt bigint[];
   dt_txt bigint[];
   dt_time bigint[];
   f_type bigint[];
BEGIN

   dt_vachr := array[1,3,4,5,13];
   dt_lng := array[6,7,8,9,10,17,18];
   dt_dt := array[11];
   dt_txt := array[2,14,15,16];
   dt_time := array[12];
   f_type[1] := NEW.attribute_fieldtype_id;

   IF(dt_vachr @> f_type) THEN
       NEW.attribute_datatype_id := 1;
   ELSIF(dt_lng @> f_type) THEN     
       NEW.attribute_datatype_id := 2;
   ELSIF(dt_dt @> f_type) THEN   
       NEW.attribute_datatype_id := 3;
   ELSIF(dt_txt @> f_type) THEN   
       NEW.attribute_datatype_id := 4;
   ELSIF(dt_time @> f_type) THEN   
       NEW.attribute_datatype_id := 5;
   ELSE
       NEW.attribute_datatype_id := 1;
   END IF;  
   RETURN NEW;
END;$$;


--
-- Name: FUNCTION maping_fieldtypes_to_datatypes(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION maping_fieldtypes_to_datatypes() IS 'Functionality : This trigger operates on the attribute_base  table.The main purpose is to assign appropriate data type to the attribute depending on the filed type selected while creating an attribute.We are not provding the option of selecting the data type while creating an attribute in the attribute create form.So this trigger maps data type depending on the filed type.';


--
-- Name: mark_import_completed(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION mark_import_completed(universal_id character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
UPDATE client_template.import_base SET completed = 'TRUE' WHERE import_id = universal_id;

END;$$;


--
-- Name: FUNCTION mark_import_completed(universal_id character varying); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION mark_import_completed(universal_id character varying) IS 'Input : Universal id
Return Type : Void
Functionality: This function takes the universal id as input and updates the time ended column in the import base table with the current time.This function is triggered on completion of loading enties form the csv file to the attribute_value_storage_base table.';


--
-- Name: mark_import_time_ended(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION mark_import_time_ended(universal_id character varying) RETURNS void
    LANGUAGE plpgsql
    AS $$BEGIN
UPDATE client_template.import_base SET time_ended = now() WHERE import_id = universal_id;

END;$$;


--
-- Name: process_audit_attribute_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_attribute_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	 audit_seq_id bigint;
BEGIN
         -- Create a row in audit_attribute_base to reflect the operation performed on attribute_base,
        IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.attribute_id,NEW.order,NEW.entity_type_id,NEW.attribute_name,NEW.attribute_datatype_id,NEW.indexed,NEW.attribute_fieldtype_id,NEW.required,NEW.regex_id ;
        ELSIF (TG_OP = 'UPDATE') THEN
	    SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.attribute_id,NEW.order,NEW.entity_type_id,NEW.attribute_name,NEW.attribute_datatype_id,NEW.indexed,NEW.attribute_fieldtype_id,NEW.required,NEW.regex_id ;
        ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.attribute_id,OLD.order,OLD.entity_type_id,OLD.attribute_name,OLD.attribute_datatype_id,OLD.indexed,OLD.attribute_fieldtype_id,OLD.required,OLD.regex_id ;
        END IF;

        IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, NEW.entity_type_id,NULL,TG_TABLE_NAME,NEW.mod_user ;
        ELSE
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, OLD.entity_type_id,NULL,TG_TABLE_NAME,OLD.mod_user ;
        END IF;    
        RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_attribute_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_attribute_base() IS 'Functionality : This tigger operates on the attribute_base table.The purpose is to insert or update the data into audit table audit_attribute_base when ever an insertion or update occur in the base table attribute_base.';


--
-- Name: process_audit_attribute_value_file_storage_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_attribute_value_file_storage_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
      audit_seq_id bigint;
      ety bigint;
BEGIN
      IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_file_storage_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.attribute_value_file_storage_id,NEW.attribute_id,NEW.entity_id,NEW."name",NEW.description,NEW.sort;
      ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_storage_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.attribute_value_file_storage_id,NEW.attribute_id,NEW.entity_id,NEW."name",NEW.description,NEW.sort;
      ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_file_storage_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.attribute_value_file_storage_id,OLD.attribute_id,OLD.entity_id,OLD."name",OLD.description,OLD.sort;
      END IF;
      
      -- IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
--             SELECT INTO ety entity_type_id FROM client_template.entity_base WHERE entity_id = NEW.entity_id;
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NEW.entity_id,TG_TABLE_NAME,NEW.mod_user ;
--       ELSE
--             SELECT INTO ety entity_type_id FROM client_template.entity_base WHERE entity_id = OLD.entity_id;
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,OLD.entity_id,TG_TABLE_NAME,OLD.mod_user ;
--       END IF;    

      RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_attribute_value_file_storage_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_attribute_value_file_storage_base() IS 'Functionality : This tigger operates on the attribute_value_file_storage_base table.The purpose is to insert or update the data into audit table audit_attribute_value_file_storage_base when ever an insertion or update occur in the base table attribute_value_file_storage_base.';


--
-- Name: process_audit_attribute_value_storage_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_attribute_value_storage_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
      audit_seq_id bigint;
      ety bigint;
BEGIN
      -- Create a row in audit_attribute_value_storage_base to reflect the operation performed on attribute_value_storage_base,
      IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_storage_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.attribute_value_id,NEW.attribute_id,NEW.entity_id,NEW.value_varchar,NEW.value_long,NEW.value_date,NEW.value_time,NEW.value_text;
      ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_storage_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.attribute_value_id,NEW.attribute_id,NEW.entity_id,NEW.value_varchar,NEW.value_long,NEW.value_date,NEW.value_time,NEW.value_text;
      ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_attribute_value_storage_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.attribute_value_id,OLD.attribute_id,OLD.entity_id,OLD.value_varchar,OLD.value_long,OLD.value_date,OLD.value_time,OLD.value_text;      
      END IF;

      -- IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
--             SELECT INTO ety entity_type_id FROM client_template.entity_base WHERE entity_id = NEW.entity_id;
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NEW.entity_id,TG_TABLE_NAME,NEW.mod_user ;
--       ELSE
--             SELECT INTO ety entity_type_id FROM client_template.entity_base WHERE entity_id = OLD.entity_id;
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,OLD.entity_id,TG_TABLE_NAME,OLD.mod_user ;
--       END IF;    
      RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_attribute_value_storage_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_attribute_value_storage_base() IS 'Functionality : This tigger operates on the attribute_value_storage_base table.The purpose is to insert or update the data into audit table audit_attribute_value_storage_base when ever an insertion or update occur in the base table attribute_value_storage_base.';


--
-- Name: process_audit_choice_attribute_detail_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_choice_attribute_detail_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	audit_seq_id bigint;
	ety bigint;
BEGIN
        -- Create a row in audit_choice_attribute_detail_base to reflect the operation performed on choice_attribute_detail_base,
        IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_choice_attribute_detail_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.choice_attribute_id,NEW.attribute_id,NEW.display_type,NEW.choice_entity_type_attribute_id ;
        ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_choice_attribute_detail_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.choice_attribute_id,NEW.attribute_id,NEW.display_type,NEW.choice_entity_type_attribute_id ;
        ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_choice_attribute_detail_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.choice_attribute_id,OLD.attribute_id,OLD.display_type,OLD.choice_entity_type_attribute_id ;
        END IF;
        IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
            SELECT INTO ety entity_type_id FROM client_template.attribute_base WHERE attribute_id = NEW.attribute_id;
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NULL,TG_TABLE_NAME,NEW.mod_user ;
        ELSE
            SELECT INTO ety entity_type_id FROM client_template.audit_attribute_base WHERE attribute_id = OLD.attribute_id;
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NULL,TG_TABLE_NAME,OLD.mod_user ;
        END IF;    
        
        RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_choice_attribute_detail_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_choice_attribute_detail_base() IS 'Functionality : This tigger operates on the choice_attribute_detail_base table.The purpose is to insert or update the data into audit table audit_choice_attribute_detail_base when ever an insertion or update occur in the base table choice_attribute_detail_base.';


--
-- Name: process_audit_entity_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_entity_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	audit_seq_id bigint;
BEGIN
        -- Create a row in audit_choice_attribute_detail_base to reflect the operation performed on choice_attribute_detail_base,
        IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.entity_id,NEW.entity_type_id ;
        ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.entity_id,NEW.entity_type_id ;
        ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.entity_id,OLD.entity_type_id ;
        END IF; 

--         IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, NEW.entity_type_id,NEW.entity_id,TG_TABLE_NAME,NEW.mod_user ;
--         ELSE
--             INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, OLD.entity_type_id,OLD.entity_id,TG_TABLE_NAME,OLD.mod_user ;
--         END IF;    
--        
        RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_entity_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_entity_base() IS 'Functionality : This tigger operates on the entity_base table.The purpose is to insert or update the data into audit table audit_entity_base when ever an insertion or update occur in the base table entity_base.';


--
-- Name: process_audit_entity_type_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_entity_type_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
	audit_seq_id bigint;
BEGIN
        -- Create a row in audit_entity_type_base to reflect the operation performed on entity_type_base,
        IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_type_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.entity_type_id,NEW.entity_type_name ;
        ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_type_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.entity_type_id,NEW.entity_type_name ;
        ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_entity_type_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.entity_type_id,OLD.entity_type_name ;
        END IF;

        IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, NEW.entity_type_id,NULL,TG_TABLE_NAME,NEW.mod_user ;
        ELSE
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, OLD.entity_type_id,NULL,TG_TABLE_NAME,OLD.mod_user ;
        END IF;    

        RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_entity_type_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_entity_type_base() IS 'Functionality : This tigger operates on the entity_type_base table.The purpose is to insert or update the data into audit table audit_entity_type_base when ever an insertion or update occur in the base table entity_type_base.';


--
-- Name: process_audit_related_entity_type_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_audit_related_entity_type_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	audit_seq_id bigint;
	ety bigint;
BEGIN
        -- Create a row in audit_related_entity_type_base to reflect the operation performed on related_entity_type_base,
        IF (TG_OP = 'INSERT') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_related_entity_type_base SELECT audit_seq_id, now(),NEW.mod_user, 1, NEW.related_entity_type_id,NEW.attribute_id,NEW.child_entity_type_id,NEW.collapse;
        ELSIF (TG_OP = 'UPDATE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_related_entity_type_base SELECT audit_seq_id, now(),NEW.mod_user, 2, NEW.related_entity_type_id,NEW.attribute_id,NEW.child_entity_type_id,NEW.collapse;
        ELSIF (TG_OP = 'DELETE') THEN
            SELECT INTO audit_seq_id nextval('audit_' || 'base_audit_id_seq');
            INSERT INTO client_template.audit_related_entity_type_base SELECT audit_seq_id, now(),OLD.mod_user, 3, OLD.related_entity_type_id,OLD.attribute_id,OLD.child_entity_type_id,OLD.collapse;
        END IF;   

        IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
            SELECT INTO ety entity_type_id FROM client_template.attribute_base WHERE attribute_id = NEW.attribute_id;
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NULL,TG_TABLE_NAME,NEW.mod_user ;
        ELSE
            SELECT INTO ety entity_type_id FROM client_template.audit_attribute_base WHERE attribute_id = OLD.attribute_id;
            INSERT INTO client_template.audit_summary_base SELECT audit_seq_id, now(),TG_OP, ety,NULL,TG_TABLE_NAME,OLD.mod_user ;
        END IF;    
     
        RETURN NEW;
END;$$;


--
-- Name: FUNCTION process_audit_related_entity_type_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_audit_related_entity_type_base() IS 'Functionality : This tigger operates on the related_entity_type_base table.The purpose is to insert or update the data into audit table audit_related_entity_type_base when ever an insertion or update occur in the base table related_entity_type_base.';


--
-- Name: process_import_base(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION process_import_base() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    import_data client_template.import_base;
    num_rec_attempted bigint;
BEGIN
    IF (TG_OP = 'INSERT') THEN
          SELECT * INTO import_data FROM ONLY client_template.import_base WHERE import_id = NEW.import_id;
          IF NOT FOUND THEN
               RETURN NEW;
          ELSE 
               num_rec_attempted := import_data.number_attempted+NEW.number_attempted;
               UPDATE client_template.import_base SET number_attempted = num_rec_attempted WHERE import_id = NEW.import_id;
               RETURN NULL;
          END IF;
   END IF;
   RETURN NEW;
END;
$$;


--
-- Name: FUNCTION process_import_base(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION process_import_base() IS 'Functionality : This trigger works on the import_base table.The main purpose is to update the number_attempted  colum on the import_base table.It checks if there is any row on the table with the current import_id if it finds then adds the current number attempted count to the already existing count otherwise it will just pass the table with the current nmuber attempted count.';


--
-- Name: regex_load(bigint, bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION regex_load(field_id bigint, att_id bigint) RETURNS SETOF regex_base
    LANGUAGE plpgsql
    AS $$
DECLARE
      regex client_template.regex_base;
      reg_id bigint;
BEGIN 
      SELECT INTO reg_id regex_id FROM ONLY client_template.attribute_base WHERE attribute_id = att_id;
      FOR regex IN (SELECT reg.* FROM ONLY client_template.regex_base reg, client_template.related_fieldtype_regex_base rel WHERE rel.regex_id=reg.regex_id AND rel.fieldtype_id=field_id)
       UNION ALL
      (SELECT * FROM ONLY client_template.regex_base WHERE regex_id=reg_id AND custom =TRUE) LOOP
        RETURN NEXT regex;
      END LOOP;  
END;$$;


--
-- Name: FUNCTION regex_load(field_id bigint, att_id bigint); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION regex_load(field_id bigint, att_id bigint) IS 'Input: Field type id and Attribute id
Return type : regex_base table.
Functionality : This procedure takes the filed type id and attribute id as input and returns the regex pattern associted with attribute for validation.
Steps Involved :
               1) With the attribute id from the input we get the regex id of the particular attribute.
               2) With the filed type id we query the regex_base , related_fieldtype_regex_base tables to pull all the regex ids associated with the field types(This is important bcz we need to pick the custom regex pattern.)';


--
-- Name: regex_load_old(bigint); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION regex_load_old(field_id bigint) RETURNS SETOF regex_base
    LANGUAGE plpgsql
    AS $$
DECLARE
      regex client_template.regex_base;     
     
BEGIN    
      SELECT INTO regex * FROM ONLY client_template.regex_base reg, client_template.related_fieldtype_regex_base rel where rel.regex_id=reg.regex_id and rel.fieldtype_id=field_id ;
      FOR regex IN SELECT * FROM ONLY client_template.regex_base WHERE regex_id = regex.regex_id LOOP
          RETURN NEXT regex;
      END LOOP;
END;$$;


--
-- Name: storage_text_search_build(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION storage_text_search_build() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    att_val client_template.attribute_value_storage_base;
BEGIN
    att_val := NEW;
    NEW.search_index := client_template.build_storage_idx_col(att_val);
    RETURN NEW;
END;$$;


--
-- Name: FUNCTION storage_text_search_build(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION storage_text_search_build() IS 'Functionality : This trigger works on the attribute_value_storage_base table ,it builds the search index column which is used for advanced search functinalty.';


--
-- Name: text_search_build(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION text_search_build() RETURNS trigger
    LANGUAGE plpgsql
    AS $$

BEGIN
    IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
        PERFORM client_template.build_entity_idx_col(NEW.entity_id);
    END IF;
    RETURN NEW;
END;
$$;


--
-- Name: FUNCTION text_search_build(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION text_search_build() IS 'Functionality : This trigger works on the entity_base table ,it builds the search index column which is used for search functinalty.';


--
-- Name: unset_index_ftypepwd(); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION unset_index_ftypepwd() RETURNS trigger
    LANGUAGE plpgsql
    AS $$BEGIN
     IF (TG_OP = 'INSERT') OR (TG_OP = 'UPDATE') THEN
        IF (NEW.attribute_fieldtype_id = 5) THEN
            NEW.indexed := FALSE ;
        ELSE
        END IF;
        RETURN NEW;
      END IF;  
END;$$;


--
-- Name: FUNCTION unset_index_ftypepwd(); Type: COMMENT; Schema: client_template; Owner: -
--

COMMENT ON FUNCTION unset_index_ftypepwd() IS 'Functionality : This trigger on attribute_value_storage_base table restricts the password data to get index so that the password data is not searchable.';

--
-- Name: user_profile_load(character varying); Type: FUNCTION; Schema: client_template; Owner: -
--

CREATE FUNCTION user_profile_load(user_name character varying) RETURNS SETOF complete_entity_base
    LANGUAGE plpgsql
    AS $$DECLARE
ent_id bigint;
BEGIN
   IF user_name = NULL THEN
       RAISE EXCEPTION 'YOU MUST PASS USER NAME';
   END IF;

   SELECT INTO ent_id entity_id FROM ONLY client_template.attribute_value_storage_base WHERE attribute_id = 125 AND value_varchar LIKE user_name;
   RETURN QUERY SELECT * FROM ONLY client_template.complete_entity_base WHERE entity_type_id = 38 AND entity_id = ent_id;
   
END;$$;


SET search_path = public, pg_catalog;

--
-- Name: recreate_proc_in_client(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION recreate_proc_in_client(proc_name character varying, client_schema character varying) RETURNS void
    LANGUAGE plpgsql COST 20
    AS $_$
DECLARE
	args character varying;
	pre_func_def character varying;
	func_def character varying;
BEGIN
	BEGIN
		EXECUTE 'SELECT pg_get_function_identity_arguments(''client_template.' || proc_name || '''::regproc)' INTO args;
		EXECUTE 'DROP FUNCTION ' || client_schema || '.' || proc_name || ' (' || args || ');'; 
	EXCEPTION WHEN undefined_function THEN
		-- do nothing 
	END;
	EXECUTE 'SELECT pg_get_functiondef(''client_template.' || proc_name || '''::regproc)' INTO pre_func_def;
	EXECUTE 'SELECT replace($$' || pre_func_def || '$$,''client_template.'',''' || client_schema || '.'');' INTO func_def;
	SELECT INTO func_def replace(func_def,'_base','_' || client_schema);
	EXECUTE func_def;
END;
$_$;


--
-- Name: setup_client(character varying, character varying); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION setup_client(schema_name_val character varying, client_name_val character varying) RETURNS void
    LANGUAGE plpgsql COST 20
    AS $$
    DECLARE
        tbltocopy character varying;
        proc_name character varying;
        viewtocopy pg_views%rowtype;
        con record;
        trig record;
BEGIN
    EXECUTE 'CREATE SCHEMA ' || schema_name_val;
    INSERT INTO client_info (client_name, schema_name) values (client_name_val, schema_name_val);
	EXECUTE 'SET search_path=' || schema_name_val || ';';
    FOR tbltocopy IN SELECT table_name FROM information_schema.tables WHERE table_schema='client_template' and table_type = 'BASE TABLE' ORDER BY table_name
    LOOP     
                
		EXECUTE 'CREATE TABLE ' || schema_name_val || '.' || replace(tbltocopy,'_base','_' || schema_name_val)
		|| ' (LIKE client_template.' || tbltocopy || ' INCLUDING CONSTRAINTS INCLUDING INDEXES) INHERITS (client_template.' || tbltocopy || ');'; 
		EXECUTE 'INSERT INTO ' || schema_name_val || '.' || replace(tbltocopy,'_base','_' || schema_name_val) || 
		' SELECT * FROM ONLY client_template.' || tbltocopy || ';';
	END LOOP;
	FOR viewtocopy IN SELECT * FROM pg_views where schemaname = 'client_template'
	LOOP
	    
		EXECUTE 'CREATE OR REPLACE VIEW ' || schema_name_val || '.' || replace(viewtocopy.viewname,'_base','_' || schema_name_val) || ' AS ' || replace(replace(viewtocopy.definition,'_base','_' || schema_name_val),'client_template',schema_name_val) || ';';
	END LOOP;
	FOR proc_name IN SELECT proname from pg_proc proc JOIN pg_type typ ON proc.prorettype = typ.oid JOIN pg_namespace nsp ON proc.pronamespace = nsp.oid WHERE nsp.nspname = 'client_template' 
	LOOP
		PERFORM public.recreate_proc_in_client(proc_name, schema_name_val);
	END LOOP;
	FOR tbltocopy IN SELECT table_name FROM information_schema.tables WHERE table_schema='client_template' and table_type = 'BASE TABLE' ORDER BY table_name
    LOOP
		FOR con IN SELECT DISTINCT ct.conname as con_name,pg_get_constraintdef(ct.oid) as con_def FROM pg_catalog.pg_attribute a JOIN pg_catalog.pg_class cl ON (a.attrelid = cl.oid AND cl.relkind = 'r') JOIN pg_catalog.pg_namespace n ON (n.oid = cl.relnamespace) JOIN pg_catalog.pg_constraint ct ON (a.attrelid = ct.conrelid AND ct.confrelid != 0 AND ct.conkey[1] = a.attnum) JOIN pg_catalog.pg_class clf ON (ct.confrelid = clf.oid AND clf.relkind = 'r') JOIN pg_catalog.pg_namespace nf ON (nf.oid = clf.relnamespace) JOIN pg_catalog.pg_attribute af ON (af.attrelid = ct.confrelid AND af.attnum = ct.confkey[1]) WHERE cl.relname = tbltocopy  and n.nspname = 'client_template'		
		LOOP	
			EXECUTE 'ALTER TABLE ' || schema_name_val || '.' || replace(tbltocopy,'_base','_' || schema_name_val) || 
			' ADD CONSTRAINT ' || con.con_name || ' ' || replace(replace(con.con_def,'_base','_' || schema_name_val),'client_template',schema_name_val);
		END LOOP;
		FOR trig IN SELECT DISTINCT pg_get_triggerdef(tg.oid) as tg_def FROM pg_catalog.pg_attribute a JOIN pg_catalog.pg_class cl ON (a.attrelid = cl.oid AND cl.relkind = 'r') JOIN pg_catalog.pg_namespace n ON (n.oid = cl.relnamespace) JOIN pg_catalog.pg_trigger tg ON (a.attrelid = tg.tgrelid) WHERE n.nspname = 'client_template' and tg.tgisinternal = false and cl.relname = tbltocopy
		LOOP	
			EXECUTE replace(replace(trig.tg_def,'_base','_' || schema_name_val),'client_template',schema_name_val);
		END LOOP;		
	END LOOP;
    RETURN;
END;
$$;


SET search_path = client_template, pg_catalog;

--
-- Name: acl_class; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE acl_class (
    id bigint NOT NULL,
    class character varying(100) NOT NULL
);


--
-- Name: acl_class_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE acl_class_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: acl_class_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE acl_class_id_seq OWNED BY acl_class.id;


--
-- Name: acl_class_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('acl_class_id_seq', 5603778, true);


--
-- Name: acl_entry; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE acl_entry (
    id bigint NOT NULL,
    acl_object_identity bigint NOT NULL,
    ace_order integer NOT NULL,
    sid bigint NOT NULL,
    mask integer NOT NULL,
    granting boolean NOT NULL,
    audit_success boolean NOT NULL,
    audit_failure boolean NOT NULL
);


--
-- Name: acl_entry_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE acl_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: acl_entry_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE acl_entry_id_seq OWNED BY acl_entry.id;


--
-- Name: acl_entry_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('acl_entry_id_seq', 8769208, true);


--
-- Name: acl_object_identity; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE acl_object_identity (
    id bigint NOT NULL,
    object_id_class bigint NOT NULL,
    object_id_identity bigint NOT NULL,
    parent_object bigint,
    owner_sid bigint,
    entries_inheriting boolean NOT NULL
);


--
-- Name: acl_object_identity_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE acl_object_identity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: acl_object_identity_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE acl_object_identity_id_seq OWNED BY acl_object_identity.id;


--
-- Name: acl_object_identity_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('acl_object_identity_id_seq', 5604109, true);


--
-- Name: acl_sid; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE acl_sid (
    id bigint NOT NULL,
    principal boolean NOT NULL,
    sid character varying(100) NOT NULL
);


--
-- Name: acl_sid_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE acl_sid_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: acl_sid_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE acl_sid_id_seq OWNED BY acl_sid.id;


--
-- Name: acl_sid_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('acl_sid_id_seq', 3921, true);


--
-- Name: attribute_datatype_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_datatype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_datatype_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE attribute_datatype_id_seq OWNED BY attribute_datatype_base.datatype_id;


--
-- Name: attribute_datatype_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_datatype_id_seq', 1, false);


--
-- Name: attribute_fieldtype_base_fieldtype_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE attribute_fieldtype_base_fieldtype_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: attribute_fieldtype_base_fieldtype_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE attribute_fieldtype_base_fieldtype_id_seq OWNED BY attribute_fieldtype_base.fieldtype_id;


--
-- Name: attribute_fieldtype_base_fieldtype_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('attribute_fieldtype_base_fieldtype_id_seq', 5, true);


--
-- Name: audit_summary_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE audit_summary_base (
    audit_id bigint NOT NULL,
    date timestamp with time zone NOT NULL,
    action character varying NOT NULL,
    entity_type_id bigint,
    entity bigint,
    table_name character varying,
    user_name character varying
);


--
-- Name: authorities; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);


--
-- Name: box_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE box_base (
    box_id bigint NOT NULL,
    user_entity_id bigint NOT NULL,
    date_added timestamp without time zone DEFAULT now()
);


--
-- Name: box_base_box_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE box_base_box_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: box_base_box_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE box_base_box_id_seq OWNED BY box_base.box_id;


--
-- Name: box_base_box_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('box_base_box_id_seq', 117, true);


--
-- Name: entity_attribute_jbent1_entity_attribute_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE entity_attribute_jbent1_entity_attribute_id_seq
    START WITH 37
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: entity_attribute_jbent1_entity_attribute_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('entity_attribute_jbent1_entity_attribute_id_seq', 37, false);


--
-- Name: import_failed_row_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE import_failed_row_base (
    id bigint NOT NULL,
    import_id character varying NOT NULL,
    row_number_failed bigint NOT NULL,
    reason text,
    data text
);


--
-- Name: import_failed_row_base_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE import_failed_row_base_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: import_failed_row_base_id_seq; Type: SEQUENCE OWNED BY; Schema: client_template; Owner: -
--

ALTER SEQUENCE import_failed_row_base_id_seq OWNED BY import_failed_row_base.id;


--
-- Name: import_failed_row_base_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('import_failed_row_base_id_seq', 424201, true);


--
-- Name: persistent_logins; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE persistent_logins (
    username character varying(64) NOT NULL,
    series character varying(64) NOT NULL,
    token character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL
);


--
-- Name: related_fieldtype_regex_id_seq; Type: SEQUENCE; Schema: client_template; Owner: -
--

CREATE SEQUENCE related_fieldtype_regex_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: related_fieldtype_regex_id_seq; Type: SEQUENCE SET; Schema: client_template; Owner: -
--

SELECT pg_catalog.setval('related_fieldtype_regex_id_seq', 6, true);


--
-- Name: related_fieldtype_regex_base; Type: TABLE; Schema: client_template; Owner: -; Tablespace: 
--

CREATE TABLE related_fieldtype_regex_base (
    id bigint DEFAULT nextval('related_fieldtype_regex_id_seq'::regclass) NOT NULL,
    fieldtype_id bigint,
    regex_id bigint
);


SET search_path = public, pg_catalog;

--
-- Name: att; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE att (
    attribute_value_id bigint,
    attribute_id bigint,
    entity_id bigint,
    value_varchar character varying(1024),
    value_long bigint,
    value_date date,
    value_text text,
    mod_user character varying,
    search_index tsvector
);


--
-- Name: audit_action; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE audit_action (
    id bigint NOT NULL,
    action character varying(24) NOT NULL
);


--
-- Name: audit_action_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE audit_action_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: audit_action_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE audit_action_id_seq OWNED BY audit_action.id;


--
-- Name: audit_action_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('audit_action_id_seq', 6, true);


--
-- Name: client_info; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE client_info (
    id bigint NOT NULL,
    client_name character varying(512) NOT NULL,
    schema_name character varying(24) NOT NULL
);


--
-- Name: client_info_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE client_info_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: client_info_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE client_info_id_seq OWNED BY client_info.id;


--
-- Name: client_info_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('client_info_id_seq', 4169, true);


--
-- Name: ent_name; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE ent_name (
    entity_type_id bigint,
    entity_type_name character varying(125),
    mod_user character varying,
    template text,
    edit_template text,
    lock_mode boolean,
    deletable boolean,
    treeable boolean
);


SET search_path = client_template, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE acl_class ALTER COLUMN id SET DEFAULT nextval('acl_class_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE acl_entry ALTER COLUMN id SET DEFAULT nextval('acl_entry_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE acl_object_identity ALTER COLUMN id SET DEFAULT nextval('acl_object_identity_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE acl_sid ALTER COLUMN id SET DEFAULT nextval('acl_sid_id_seq'::regclass);


--
-- Name: datatype_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE attribute_datatype_base ALTER COLUMN datatype_id SET DEFAULT nextval('attribute_datatype_id_seq'::regclass);


--
-- Name: fieldtype_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE attribute_fieldtype_base ALTER COLUMN fieldtype_id SET DEFAULT nextval('attribute_fieldtype_base_fieldtype_id_seq'::regclass);


--
-- Name: attribute_value_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE attribute_value_base ALTER COLUMN attribute_value_id SET DEFAULT nextval('attribute_value_base_id_seq'::regclass);


--
-- Name: box_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE box_base ALTER COLUMN box_id SET DEFAULT nextval('box_base_box_id_seq'::regclass);


--
-- Name: entity_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE entity_base ALTER COLUMN entity_id SET DEFAULT nextval('entity_entity_id_seq'::regclass);


--
-- Name: entity_type_id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE entity_type_base ALTER COLUMN entity_type_id SET DEFAULT nextval('entity_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: client_template; Owner: -
--

ALTER TABLE import_failed_row_base ALTER COLUMN id SET DEFAULT nextval('import_failed_row_base_id_seq'::regclass);


SET search_path = public, pg_catalog;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE audit_action ALTER COLUMN id SET DEFAULT nextval('audit_action_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE client_info ALTER COLUMN id SET DEFAULT nextval('client_info_id_seq'::regclass);


SET search_path = client_template, pg_catalog;

--
-- Data for Name: acl_class; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY acl_class (id, class) FROM stdin;
34546	entityType_38
34547	entityType_40
34548	entity_15
34549	entity_16
34550	entity_18
34551	entity_19
34552	entity_20
34553	entity_21
34554	entity_22
34555	entity_23
37197	attribute_125
37198	attribute_128
37199	attribute_130
37200	attribute_131
37201	attribute_132
37919	entityType_41
37920	attribute_133
37921	attribute_134
37922	attribute_135
37923	attribute_136
37924	attribute_137
\.


--
-- Data for Name: acl_entry; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) FROM stdin;
180828	34568	0	3105	2	t	f	f
180829	34569	0	3105	2	t	f	f
180830	34570	0	3105	2	t	f	f
180831	34571	0	3105	2	t	f	f
180832	34572	0	3105	2	t	f	f
180833	34573	0	3105	2	t	f	f
180834	34574	0	3105	2	t	f	f
180835	34575	0	3105	2	t	f	f
193217	37217	0	3505	1	t	f	f
193218	37217	1	3105	1	t	f	f
193219	37217	2	3107	1	t	f	f
193223	37218	0	3505	1	t	f	f
193224	37218	1	3105	1	t	f	f
193225	37218	2	3107	1	t	f	f
193229	37219	0	3505	1	t	f	f
193230	37219	1	3105	1	t	f	f
193231	37219	2	3107	1	t	f	f
193235	37220	0	3505	1	t	f	f
193236	37220	1	3105	1	t	f	f
193237	37220	2	3107	1	t	f	f
193241	37221	0	3505	1	t	f	f
193242	37221	1	3105	1	t	f	f
193243	37221	2	3107	1	t	f	f
196752	37940	0	3505	1	t	f	f
196753	37940	1	3105	1	t	f	f
196754	37940	2	3107	1	t	f	f
196758	37941	0	3505	1	t	f	f
196759	37941	1	3105	1	t	f	f
196760	37941	2	3107	1	t	f	f
196764	37942	0	3505	1	t	f	f
196765	37942	1	3105	1	t	f	f
196766	37942	2	3107	1	t	f	f
196770	37943	0	3505	1	t	f	f
196771	37943	1	3105	1	t	f	f
196772	37943	2	3107	1	t	f	f
196776	37944	0	3505	1	t	f	f
196777	37944	1	3105	1	t	f	f
196778	37944	2	3107	1	t	f	f
\.


--
-- Data for Name: acl_object_identity; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) FROM stdin;
34566	34546	38	\N	3104	t
34567	34547	40	\N	3104	t
34568	34548	15	34566	3104	t
34569	34549	16	34567	3104	t
34570	34550	18	34567	3104	t
34571	34551	19	34566	3104	t
34572	34552	20	34566	3104	t
34573	34553	21	34567	3104	t
34574	34554	22	34567	3104	t
34575	34555	23	34566	3104	t
37217	37197	125	\N	3104	t
37218	37198	128	\N	3104	t
37219	37199	130	\N	3104	t
37220	37200	131	\N	3104	t
37221	37201	132	\N	3104	t
37939	37919	41	\N	3104	t
37940	37920	133	\N	3104	t
37941	37921	134	\N	3104	t
37942	37922	135	\N	3104	t
37943	37923	136	\N	3104	t
37944	37924	137	\N	3104	t
\.


--
-- Data for Name: acl_sid; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY acl_sid (id, principal, sid) FROM stdin;
3104	t	jbent
3105	f	ROLE_MANAGER
3107	f	ROLE_STAFF
3505	f	ROLE_USER
\.


--
-- Data for Name: attribute_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_base (attribute_id, "order", entity_type_id, attribute_name, attribute_datatype_id, indexed, attribute_fieldtype_id, required, mod_user, regex_id, display, deletable) FROM stdin;
136	4	41	User	1	t	3	f	jbent	\N	f	f
135	3	41	Share	1	t	3	t	jbent	\N	t	f
133	1	41	query name	1	t	1	t	jbent	9	t	f
132	3	40	Assignatnew	1	t	1	t	jbent	9	t	f
137	5	41	search type	1	t	3	\N	jbent	\N	f	f
134	2	41	syntax	4	f	2	t	jbent	9	t	f
138	6	41	saved query	1	t	1	\N	jbent	9	f	f
127	4	38	Email Address	1	t	13	t	jbent	6	t	f
126	3	38	Profile Image	4	t	14	f	jbent	\N	t	f
125	1	38	Username	1	t	1	t	jbent	9	t	f
128	2	38	Password	1	f	5	t	jbent	9	t	f
131	2	40	Roledescription	4	t	2	t	jbent	9	t	f
130	1	40	Rolename	1	t	1	t	jbent	9	t	f
120	1	39	From	2	t	17	t	jbent	9	t	f
121	2	39	Name	1	t	1	t	jbent	9	t	f
122	3	39	To	2	t	17	t	jbent	9	t	f
141	1	42	Title	1	t	1	t	jbent	9	t	f
142	2	42	Date	3	t	11	t	jbent	3	t	f
143	3	42	Repeats	1	t	1	t	jbent	9	t	f
144	4	42	RepetitionEndsOn	3	t	11	t	jbent	3	t	f
145	5	42	Description	4	t	2	t	jbent	9	t	f
146	1	43	Name	1	t	1	t	jbent	9	t	f
150	5	38	facebook appId	1	t	1	f	jbent	\N	t	f
151	6	38	facebook appSecret	1	t	1	f	jbent	\N	t	f
152	7	38	facebook pageId	1	t	1	f	jbent	\N	t	f
153	8	38	twitter consumerKey	1	t	1	f	jbent	\N	t	f
154	9	38	twitter consumerSecret	1	t	1	f	jbent	\N	t	f
155	10	38	twitter accessToken	1	t	1	f	jbent	\N	t	f
156	11	38	twitter accessTokenSecret	1	t	1	f	jbent	\N	t	f
157	1	45	Username	1	t	1	t	jbent	9	t	f
158	2	45	Syncedon	1	t	1	t	jbent	9	t	f
170	1	44	Name	1	t	1	t	jbent	9	t	f
171	2	44	File	4	t	15	t	jbent	\N	t	f
\.


--
-- Data for Name: attribute_datatype_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_datatype_base (datatype_id, datatype_name) FROM stdin;
1	varchar
5	time
3	date
4	text
2	long
\.


--
-- Data for Name: attribute_fieldtype_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_fieldtype_base (fieldtype_id, fieldtype_name, display_order, display_label) FROM stdin;
3	choice	3	Choice
5	password	5	Password
6	ssn	6	Social Security Number
7	phone_us	7	US Phone Number xxx-xxx-xxxx
10	currency	10	Currency $xxx.xx
9	zip_long	9	Zip Code xxxxx-xxxx
8	zip_short	8	Zip Code xxxxx
11	date	11	Date
12	time	12	Time
14	image	14	Image
15	file	15	File
1	text	1	Text Field
2	text_area	2	Text Box
4	entity_type	4	Related Type
16	WYSIWYG	16	WYSIWYG
17	numeric	17	Numeric
18	decimal	18	Decimal
13	email	13	Email Address
\.


--
-- Data for Name: attribute_fieldtype_option_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_fieldtype_option_base (fieldtype_option_id, attribute_fieldtype_id, option) FROM stdin;
1	14	Number of Images Allow
2	14	Extensions to Allow
3	15	File Types to Allow
\.


--
-- Data for Name: attribute_fieldtype_option_value_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_fieldtype_option_value_base (option_value_id, attribute_id, attribute_fieldtype_option_id, value) FROM stdin;
\.


--
-- Data for Name: attribute_value_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_value_base (attribute_value_id, attribute_id, entity_id) FROM stdin;
\.


--
-- Data for Name: attribute_value_file_storage_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_value_file_storage_base (attribute_value_file_storage_id, attribute_id, entity_id, name, description, sort, mod_user) FROM stdin;
\.


--
-- Data for Name: attribute_value_storage_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY attribute_value_storage_base (attribute_value_id, attribute_id, entity_id, value_varchar, value_long, value_date, value_text, mod_user, search_index) FROM stdin;
49	128	15	a564de63c2d0da68cf47586ee05984d7	\N	\N	\N	jbent	\N
50	130	16	ROLE_ADMINISTRATOR	\N	\N	\N	jbent	'administr':2 'role':1
52	130	18	ROLE_USER	\N	\N	\N	jbent	'role':1 'user':2
53	125	19	brent	\N	\N	\N	jbent	'brent':1
54	128	19	65d15fe9156f9c4bbffd98085992a44e	\N	\N	\N	jbent	'65d15fe9156f9c4bbffd98085992a44e':1
55	125	20	ravit	\N	\N	\N	jbent	'ravit':1
56	128	20	22b5c9accc6e1ba628cedc63a72d57f8	\N	\N	\N	jbent	'22b5c9accc6e1ba628cedc63a72d57f8':1
57	130	21	ROLE_MANAGER	\N	\N	\N	jbent	'manag':2 'role':1
59	132	16	yes	\N	\N	\N	jbent	'yes':1
58	130	22	ROLE_STAFF	\N	\N	\N	jbent	'role':1 'staff':2
61	132	18	no	\N	\N	\N	jbent	
62	132	21	yes	\N	\N	\N	jbent	'yes':1
63	132	22	yes	\N	\N	\N	jbent	'yes':1
65	128	23	2b58af6dddbd072ed27ffc86725d7d3a	\N	\N	\N	jbent	'2b58af6dddbd072ed27ffc86725d7d3a':1
66	131	16	\N	\N	\N	Administrator	jbent	'administr':1
67	131	21	\N	\N	\N	Manager	jbent	'manag':1
68	131	22	\N	\N	\N	Staff	jbent	'staff':1
64	125	23	jbent1	\N	\N	\N	jbent	'jbent1':1
48	125	15	jbent	\N	\N	\N	jbent	'jbent':1
69	131	18	\N	\N	\N	TestRole	jbent	'testrol':1
70	146	24	ROOT	\N	\N	\N	jbent	'root':1
\.


--
-- Data for Name: audit_attribute_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_attribute_base (audit_id, date, mod_user, audit_action, attribute_id, "order", entity_type_id, attribute_name, attribute_datatype_id, indexed, attribute_fieldtype_id, required, regex_id, display, deletable) FROM stdin;
888712	2010-08-14 14:56:15.64+05:30	jbent	1	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534766	2011-04-18 11:47:07.343+05:30	jbent	1	135	3	41	Share	1	t	3	t	\N	t	f
1534764	2011-04-18 11:44:02.562+05:30	jbent	1	133	1	41	query name	1	t	1	t	9	t	f
407	2010-06-02 18:28:43.328+05:30	jbent	1	128	2	38	Password	1	f	5	t	9	t	f
5799	2010-07-07 21:10:03.515+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
5801	2010-07-07 21:10:05.5+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
5803	2010-07-07 21:10:07.562+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
5805	2010-07-07 21:10:18.156+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
5811	2010-07-07 21:10:50.734+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1505444	2011-01-27 17:24:20.906+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534677	2011-04-07 18:48:18.75+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534771	2011-04-18 12:56:52.921+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
888709	2010-08-14 14:51:30.156+05:30	jbent	1	131	2	40	Roledescription	1	t	2	t	9	t	f
888710	2010-08-14 14:52:25.171+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1480355	2010-12-23 15:52:12.796+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1480360	2010-12-23 16:15:49.718+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1480369	2010-12-23 16:26:03.218+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1480372	2010-12-23 16:28:50.75+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1480375	2010-12-23 16:30:42.437+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1507563	2011-02-04 19:16:50.25+05:30	jbent	2	131	2	40	Roledescription	1	t	2	t	9	t	f
1534803	2011-04-18 12:58:06.156+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534804	2011-04-18 12:58:09+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534767	2011-04-18 11:48:27.328+05:30	jbent	1	136	4	41	User	1	t	3	f	\N	f	f
1534820	2011-04-18 12:58:45.671+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534818	2011-04-18 12:58:41.343+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534819	2011-04-18 12:58:43.156+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534814	2011-04-18 12:58:32.906+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534821	2011-04-18 12:58:47.515+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534850	2011-04-21 17:25:20.359+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534815	2011-04-18 12:58:34.921+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534852	2011-04-21 17:25:27.89+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534854	2011-04-21 17:25:32.656+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534805	2011-04-18 12:58:10.781+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534806	2011-04-18 12:58:12.437+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534807	2011-04-18 12:58:14.921+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534808	2011-04-18 12:58:16.609+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534849	2011-04-21 17:25:15.343+05:30	jbent	1	137	5	41	search type	1	t	3	\N	\N	f	f
1534918	2011-04-21 17:27:46.578+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534920	2011-04-21 17:27:49.703+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534856	2011-04-21 17:25:35.437+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534858	2011-04-21 17:25:52.64+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534860	2011-04-21 17:25:55.609+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534862	2011-04-21 17:25:59+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534922	2011-04-21 17:27:51.984+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534864	2011-04-21 17:26:02+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534866	2011-04-21 17:26:04.062+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534868	2011-04-21 17:26:06.468+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	f	f
1534870	2011-04-21 17:26:12.234+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	f
1534876	2011-04-21 17:26:49.906+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	f
1534878	2011-04-21 17:26:54.14+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	f
1534880	2011-04-21 17:26:57.328+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	f
1534882	2011-04-21 17:26:59.328+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	f
1534872	2011-04-21 17:26:17.093+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534884	2011-04-21 17:27:02.703+05:30	jbent	2	136	4	41	User	1	t	3	f	\N	t	t
1534874	2011-04-21 17:26:25.406+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534886	2011-04-21 17:27:04.859+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534888	2011-04-21 17:27:08.39+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534890	2011-04-21 17:27:10.437+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534892	2011-04-21 17:27:12.687+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534894	2011-04-21 17:27:17.75+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534896	2011-04-21 17:27:26.343+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534898	2011-04-21 17:27:28.484+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	f
1534900	2011-04-21 17:27:30.203+05:30	jbent	2	135	3	41	Share	1	t	3	t	\N	t	t
1534924	2011-04-21 17:27:54.812+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1509216	2011-02-07 16:46:14.625+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534926	2011-04-21 17:27:56.75+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534928	2011-04-21 17:27:58.859+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534930	2011-04-21 17:28:03.671+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	f
1534932	2011-04-21 17:28:07.515+05:30	jbent	2	133	1	41	query name	1	t	1	t	9	t	t
910004	2010-09-24 21:29:59.968+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
910027	2010-09-24 21:37:42.375+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1480356	2010-12-23 15:52:12.796+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1480361	2010-12-23 16:15:49.718+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1480370	2010-12-23 16:26:03.218+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1480373	2010-12-23 16:28:50.75+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1480376	2010-12-23 16:30:42.437+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1507564	2011-02-04 19:16:50.25+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534678	2011-04-07 18:48:18.75+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534809	2011-04-18 12:58:18.625+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534810	2011-04-18 12:58:22.187+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534811	2011-04-18 12:58:24.421+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534812	2011-04-18 12:58:27.734+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534813	2011-04-18 12:58:30.328+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	f
1534934	2011-04-21 17:28:22.734+05:30	jbent	2	132	3	40	Assignatnew	1	t	1	t	9	t	t
1534936	2011-04-21 17:28:38.5+05:30	jbent	2	137	5	41	search type	1	t	3	\N	\N	f	t
1534937	2011-04-21 17:28:49.718+05:30	jbent	2	137	5	41	search type	1	t	3	\N	\N	t	t
1534765	2011-04-18 11:45:02.984+05:30	jbent	1	134	2	41	syntax	4	f	2	t	9	t	f
1534816	2011-04-18 12:58:37.109+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534817	2011-04-18 12:58:39.671+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534902	2011-04-21 17:27:31.921+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534904	2011-04-21 17:27:33.671+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534906	2011-04-21 17:27:35.437+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534908	2011-04-21 17:27:37.156+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534910	2011-04-21 17:27:38.875+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534912	2011-04-21 17:27:40.468+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534914	2011-04-21 17:27:42.375+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	f
1534916	2011-04-21 17:27:44.921+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	t
1541796	2011-05-17 16:29:18.234+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	t
1541798	2011-05-17 16:30:43.125+05:30	jbent	2	134	2	41	syntax	4	f	2	t	9	t	t
1547119	2011-06-23 14:37:09.546+05:30	jbent	1	138	6	41	saved query	1	t	1	\N	9	t	t
1547121	2011-06-23 14:44:10.64+05:30	jbent	1	127	4	38	Email Address	1	t	13	t	6	t	t
1547120	2011-06-23 14:41:58.156+05:30	jbent	1	126	3	38	Profile Image	1	t	14	f	\N	t	t
1547122	2011-06-23 14:46:57.546+05:30	jbent	2	126	3	38	Profile Image	1	t	14	f	\N	t	t
406	2010-06-02 18:27:04.265+05:30	jbent	1	125	1	38	Username	1	t	1	t	9	t	f
5807	2010-07-07 21:10:42.328+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
5809	2010-07-07 21:10:44.64+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534676	2011-04-07 18:48:18.75+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534768	2011-04-18 12:56:45.406+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534769	2011-04-18 12:56:48.781+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534770	2011-04-18 12:56:51.156+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534784	2011-04-18 12:57:19.89+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1534785	2011-04-18 12:57:22.5+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	f
1547123	2011-06-23 14:49:24.14+05:30	jbent	2	125	1	38	Username	1	t	1	t	9	t	t
1534772	2011-04-18 12:56:54.718+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534773	2011-04-18 12:56:56.625+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534774	2011-04-18 12:56:59.109+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534775	2011-04-18 12:57:00.812+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534776	2011-04-18 12:57:02.687+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534777	2011-04-18 12:57:04.484+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534782	2011-04-18 12:57:15.14+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1534783	2011-04-18 12:57:17.5+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	f
1547125	2011-06-23 14:50:02.765+05:30	jbent	2	128	2	38	Password	1	f	5	t	9	t	t
1509237	2011-02-07 17:55:35.656+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534792	2011-04-18 12:57:37.453+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534793	2011-04-18 12:57:39.296+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534794	2011-04-18 12:57:41.156+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534795	2011-04-18 12:57:42.89+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534796	2011-04-18 12:57:45.14+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534797	2011-04-18 12:57:47.625+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534798	2011-04-18 12:57:50.296+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534799	2011-04-18 12:57:52.578+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534800	2011-04-18 12:57:55.5+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534801	2011-04-18 12:57:57.625+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1534802	2011-04-18 12:58:03.218+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	f
1547129	2011-06-23 14:51:41.39+05:30	jbent	2	131	2	40	Roledescription	4	t	2	t	9	t	t
888711	2010-08-14 14:53:17.75+05:30	jbent	1	130	1	40	Rolename	1	t	1	t	9	t	f
888713	2010-08-14 14:56:25.984+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1480354	2010-12-23 15:52:12.796+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1480359	2010-12-23 16:15:49.718+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1480368	2010-12-23 16:26:03.218+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1480371	2010-12-23 16:28:50.75+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1480374	2010-12-23 16:30:42.437+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1507562	2011-02-04 19:16:50.25+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1509236	2011-02-07 17:55:10.781+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534778	2011-04-18 12:57:06.812+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534779	2011-04-18 12:57:08.453+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534780	2011-04-18 12:57:10.171+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534781	2011-04-18 12:57:12.312+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534786	2011-04-18 12:57:27.109+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534787	2011-04-18 12:57:28.953+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534788	2011-04-18 12:57:30.546+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534789	2011-04-18 12:57:31.968+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534790	2011-04-18 12:57:33.484+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1534791	2011-04-18 12:57:35.812+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	f
1547127	2011-06-23 14:51:24.718+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	t
1549744	2011-07-13 15:29:37.484+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	t
37337243	2011-08-12 17:38:42.703+05:30	jbent	2	130	1	40	Rolename	1	t	1	t	9	t	t
\.


--
-- Data for Name: audit_attribute_value_file_storage_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_attribute_value_file_storage_base (audit_id, date, mod_user, audit_action, attribute_value_file_storage_id, attribute_id, entity_id, name, description, sort) FROM stdin;
\.


--
-- Data for Name: audit_attribute_value_storage_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_attribute_value_storage_base (audit_id, date, mod_user, audit_action, attribute_value_id, attribute_id, entity_id, value_varchar, value_long, value_date, value_text, search_index) FROM stdin;
351	2010-05-31 03:46:37.511634+05:30	jbent	1	49	128	15	a564de63c2d0da68cf47586ee05984d7	\N	\N	\N	\N
888714	2010-08-14 14:59:40.765+05:30	jbent	1	50	130	16	ROLE_ADMINISTRATOR	\N	\N	\N	\N
889122	2010-08-16 12:30:50.187+05:30	jbent	2	50	130	16	ROLE_ADMINISTRATOR	\N	\N	\N	\N
889124	2010-08-16 12:31:15.203+05:30	jbent	2	50	130	16	ROLE_ADMINISTRATOR	\N	\N	\N	\N
888718	2010-08-14 15:01:01.468+05:30	jbent	1	52	130	18	ROLE_USER	\N	\N	\N	\N
889130	2010-08-16 12:32:31.625+05:30	jbent	2	52	130	18	ROLE_USER	\N	\N	\N	\N
889132	2010-08-16 12:32:48.75+05:30	jbent	2	52	130	18	ROLE_USER	\N	\N	\N	\N
909951	2010-09-24 20:51:48.812+05:30	jbent	1	53	125	19	brent	\N	\N	\N	\N
909953	2010-09-24 20:52:46.453+05:30	jbent	1	54	128	19	65d15fe9156f9c4bbffd98085992a44e	\N	\N	\N	\N
909955	2010-09-24 20:53:05.218+05:30	jbent	1	55	125	20	ravit	\N	\N	\N	\N
909957	2010-09-24 20:53:41.406+05:30	jbent	1	56	128	20	22b5c9accc6e1ba628cedc63a72d57f8	\N	\N	\N	\N
909961	2010-09-24 21:03:30.39+05:30	jbent	2	56	128	20	22b5c9accc6e1ba628cedc63a72d57f8	\N	\N	\N	\N
910794	2010-10-01 11:23:21.406+05:30	jbent	1	57	130	21	ROLE_MANAGER	\N	\N	\N	\N
910798	2010-10-01 11:23:58.296+05:30	jbent	1	59	132	16	yes	\N	\N	\N	\N
910800	2010-10-01 11:24:15.015+05:30	jbent	1	60	132	17	yes	\N	\N	\N	\N
910796	2010-10-01 11:23:41.937+05:30	jbent	1	58	130	22	ROLE_STAFF	\N	\N	\N	\N
910802	2010-10-01 11:24:35.437+05:30	jbent	2	58	130	22	ROLE_STAFF	\N	\N	\N	\N
910804	2010-10-01 11:25:03.234+05:30	jbent	1	61	132	18	no	\N	\N	\N	\N
910806	2010-10-01 11:25:26.359+05:30	jbent	1	62	132	21	yes	\N	\N	\N	\N
910808	2010-10-01 11:25:44.093+05:30	jbent	1	63	132	22	yes	\N	\N	\N	\N
912141	2010-10-14 19:15:46.296+05:30	jbent	2	63	132	22	yes	\N	\N	\N	\N
912146	2010-10-14 19:19:13.453+05:30	jbent	1	65	128	23	2b58af6dddbd072ed27ffc86725d7d3a	\N	\N	\N	\N
1529313	2011-03-18 19:37:23.859+05:30	jbent	1	66	131	16	\N	\N	\N	Administrator	\N
1529315	2011-03-18 19:37:50.312+05:30	jbent	1	67	131	21	\N	\N	\N	Manager	\N
1529317	2011-03-18 19:38:10.343+05:30	jbent	1	68	131	22	\N	\N	\N	Staff	\N
912144	2010-10-14 19:18:07.375+05:30	jbent	1	64	125	23	jbent1	\N	\N	\N	\N
1541786	2011-05-17 16:23:15.406+05:30	jbent	2	64	125	23	jbent1	\N	\N	\N	\N
350	2010-05-31 03:45:11.120335+05:30	jbent	1	48	125	15	jbent	\N	\N	\N	'jbent':1
1541791	2011-05-17 16:23:56.109+05:30	jbent	2	48	125	15	jbent	\N	\N	\N	\N
1529319	2011-03-18 19:38:30.765+05:30	jbent	1	69	131	18	\N	\N	\N	TestRole	\N
1529321	2011-03-18 19:41:38.609+05:30	jbent	2	69	131	18	\N	\N	\N	TestRole	\N
1549734	2011-07-13 12:24:50.937+05:30	jbent	2	69	131	18	\N	\N	\N	TestRole	\N
\.


--
-- Data for Name: audit_choice_attribute_detail_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_choice_attribute_detail_base (audit_id, date, mod_user, audit_action, choice_attribute_id, attribute_id, display_type, choice_entity_type_attribute_id) FROM stdin;
\.


--
-- Data for Name: audit_entity_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_entity_base (audit_id, date, mod_user, audit_action, entity_id, entity_type_id, search_index, entity_valid) FROM stdin;
909949	2010-09-24 20:50:13.484+05:30	jbent	1	19	38	'65d15fe9156f9c4bbffd98085992a44e':2B 'brent':1A	f
909952	2010-09-24 20:51:48.812+05:30	jbent	2	19	38	'65d15fe9156f9c4bbffd98085992a44e':2B 'brent':1A	f
909954	2010-09-24 20:52:46.453+05:30	jbent	2	19	38	\N	f
909950	2010-09-24 20:50:23.812+05:30	jbent	1	20	38	'22b5c9accc6e1ba628cedc63a72d57f8':2B 'ravit':1A	f
909956	2010-09-24 20:53:05.218+05:30	jbent	2	20	38	'22b5c9accc6e1ba628cedc63a72d57f8':2B 'ravit':1A	f
909958	2010-09-24 20:53:41.406+05:30	jbent	2	20	38	'22b5c9accc6e1ba628cedc63a72d57f8':2B 'ravit':1A	f
909962	2010-09-24 21:03:30.39+05:30	jbent	2	20	38	\N	f
1505437	2011-01-27 17:17:49.203+05:30	jbent	2	19	38	\N	f
1505438	2011-01-27 17:17:49.203+05:30	jbent	2	20	38	\N	f
888706	2010-08-14 14:47:14.765+05:30	jbent	1	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
888715	2010-08-14 14:59:40.765+05:30	jbent	2	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
889123	2010-08-16 12:30:50.187+05:30	jbent	2	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
889125	2010-08-16 12:31:15.203+05:30	jbent	2	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
910799	2010-10-01 11:23:58.296+05:30	jbent	2	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
1505439	2011-01-27 17:17:49.203+05:30	jbent	2	16	40	'administr':2A,4B 'role':1A 'yes':3C	f
1529314	2011-03-18 19:37:23.859+05:30	jbent	2	16	40	\N	f
910792	2010-10-01 11:22:38.718+05:30	jbent	1	21	40	'manag':2A,4B 'role':1A 'yes':3C	f
910795	2010-10-01 11:23:21.406+05:30	jbent	2	21	40	'manag':2A,4B 'role':1A 'yes':3C	f
910807	2010-10-01 11:25:26.359+05:30	jbent	2	21	40	'manag':2A,4B 'role':1A 'yes':3C	f
1505441	2011-01-27 17:17:49.203+05:30	jbent	2	21	40	'manag':2A,4B 'role':1A 'yes':3C	f
1529316	2011-03-18 19:37:50.312+05:30	jbent	2	21	40	\N	f
910793	2010-10-01 11:22:46.218+05:30	jbent	1	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
910797	2010-10-01 11:23:41.937+05:30	jbent	2	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
910803	2010-10-01 11:24:35.437+05:30	jbent	2	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
910809	2010-10-01 11:25:44.093+05:30	jbent	2	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
912142	2010-10-14 19:15:46.296+05:30	jbent	2	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
1505442	2011-01-27 17:17:49.203+05:30	jbent	2	22	40	'role':1A 'staff':2A,4B 'yes':3C	f
1529318	2011-03-18 19:38:10.343+05:30	jbent	2	22	40	\N	f
912143	2010-10-14 19:16:45.14+05:30	jbent	1	23	38	\N	f
912145	2010-10-14 19:18:07.375+05:30	jbent	2	23	38	\N	f
912147	2010-10-14 19:19:13.453+05:30	jbent	2	23	38	\N	f
1505443	2011-01-27 17:17:49.203+05:30	jbent	2	23	38	\N	f
1529320	2011-03-18 19:38:30.765+05:30	jbent	2	23	38	\N	f
1541787	2011-05-17 16:23:15.406+05:30	jbent	2	23	38	\N	f
349	2010-05-31 03:21:43.567235+05:30	jbent	1	15	38	\N	f
8647	2010-07-13 00:29:39.046+05:30	jbent	2	15	38	\N	f
8649	2010-07-13 00:32:20.796+05:30	jbent	2	15	38	\N	f
8651	2010-07-13 00:32:32.75+05:30	jbent	2	15	38	\N	f
8653	2010-07-13 00:32:45.75+05:30	jbent	2	15	38	\N	f
8655	2010-07-13 00:33:04.953+05:30	jbent	2	15	38	\N	f
8657	2010-07-13 00:34:09.25+05:30	jbent	2	15	38	\N	f
8806	2010-07-13 01:10:53.828+05:30	jbent	2	15	38	\N	f
1505436	2011-01-27 17:17:49.203+05:30	jbent	2	15	38	\N	f
1541792	2011-05-17 16:23:56.109+05:30	jbent	2	15	38	\N	f
888708	2010-08-14 14:47:41.953+05:30	jbent	1	18	40	'role':1A 'testrol':3B 'user':2A	f
888719	2010-08-14 15:01:01.468+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
889131	2010-08-16 12:32:31.625+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
889133	2010-08-16 12:32:48.75+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
910805	2010-10-01 11:25:03.234+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
1505440	2011-01-27 17:17:49.203+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
1529322	2011-03-18 19:41:38.609+05:30	jbent	2	18	40	'role':1A 'testrol':3B 'user':2A	f
1549735	2011-07-13 12:24:50.937+05:30	jbent	2	18	40	\N	f
\.


--
-- Data for Name: audit_entity_type_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_entity_type_base (audit_id, date, mod_user, audit_action, entity_type_id, entity_type_name, template, edit_template, lock_mode, deletable, treeable) FROM stdin;
346	2010-05-31 02:38:22.737205+05:30	jbent	1	38	People	\N	\N	f	t	f
888705	2010-08-14 14:43:21.703+05:30	jbent	1	40	Role	\N	\N	f	t	f
1534763	2011-04-18 11:42:02.187+05:30	jbent	1	41	Saved Searches	\N	\N	f	t	f
\.


--
-- Data for Name: audit_related_entity_type_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_related_entity_type_base (audit_id, date, mod_user, audit_action, related_entity_type_id, attribute_id, child_entity_type_id, collapse) FROM stdin;
\.


--
-- Data for Name: audit_summary_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY audit_summary_base (audit_id, date, action, entity_type_id, entity, table_name, user_name) FROM stdin;
1549734	2011-07-13 12:24:50.937+05:30	UPDATE	40	18	attribute_value_storage_base	jbent
1549735	2011-07-13 12:24:50.937+05:30	UPDATE	40	18	entity_base	jbent
1549744	2011-07-13 15:29:37.484+05:30	UPDATE	40	\N	attribute_base	jbent
37337243	2011-08-12 17:38:42.703+05:30	UPDATE	40	\N	attribute_base	jbent
\.


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY authorities (username, authority) FROM stdin;
jbent	ROLE_ADMINISTRATOR
brent	ROLE_MANAGER
ravit	ROLE_STAFF
jbent	ROLE_MANAGER
jbent1	ROLE_USER
\.


--
-- Data for Name: box_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY box_base (box_id, user_entity_id, date_added) FROM stdin;
\.


--
-- Data for Name: box_base_entity_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY box_base_entity_base (box_id, entity_id) FROM stdin;
\.


--
-- Data for Name: bulk_entity_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY bulk_entity_base (entity_id, entity_type_id, mod_user, search_index, entity_valid, attribute_value_id, attribute_id, value_varchar, value_long, value_date, value_text, counter) FROM stdin;
\.


--
-- Data for Name: choice_attribute_detail_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY choice_attribute_detail_base (choice_attribute_id, attribute_id, display_type, choice_entity_type_attribute_id, mod_user) FROM stdin;
\.


--
-- Data for Name: entity_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY entity_base (entity_id, entity_type_id, mod_user, search_index, entity_valid) FROM stdin;
19	38	jbent	'65d15fe9156f9c4bbffd98085992a44e':2B 'brent':1A	t
20	38	jbent	'22b5c9accc6e1ba628cedc63a72d57f8':2B 'ravit':1A	t
16	40	jbent	'administr':2A,4B 'role':1A 'yes':3C	t
21	40	jbent	'manag':2A,4B 'role':1A 'yes':3C	t
22	40	jbent	'role':1A 'staff':2A,4B 'yes':3C	t
23	38	jbent	\N	t
15	38	jbent	\N	t
18	40	jbent	'role':1A 'testrol':3B 'user':2A	t
24	43	jbent	'root':1A	t
\.


--
-- Data for Name: entity_type_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY entity_type_base (entity_type_id, entity_type_name, mod_user, template, edit_template, lock_mode, deletable, treeable) FROM stdin;
38	People	jbent	\N	\N	f	f	f
39	Relation	jbent	\N	\N	f	f	f
40	Role	jbent	\N	\N	f	f	f
41	Saved Searches	jbent	\N	\N	f	f	f
42	Event	jbent	\N	\N	f	f	f
43	Folder	jbent	\N	\N	f	f	f
44	Document	jbent	\N	\N	f	f	t
45	FolderSyncDetails	jbent	\N	\N	f	f	f
\.


--
-- Data for Name: import_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY import_base (import_id, number_attempted, number_failed, time_start, time_ended, completed) FROM stdin;
\.


--
-- Data for Name: import_failed_row_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY import_failed_row_base (id, import_id, row_number_failed, reason, data) FROM stdin;
\.


--
-- Data for Name: persistent_logins; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY persistent_logins (username, series, token, last_used) FROM stdin;
\.


--
-- Data for Name: regex_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY regex_base (regex_id, pattern, display_name, custom) FROM stdin;
6	[a-z0-9_]+([\\\\-\\\\.][a-z0-9_]+)*(@)[a-z0-9_]+([\\\\-\\\\.][a-z0-9_]+)+	Email Address	f
8	^[+-]?[0-9]{1,3}(?:,?[0-9]{3})*(?:\\.[0-9]{2})?$	Currency	f
1	^[a-zA-Z0-9]+$	Letters and Numbers	f
2	^[0-9]+$	Numbers\n	f
0	For referential Integrity	\N	\N
4	^(\\d{3}-\\d{2}-\\d{4})|(\\d{3}\\d{2}\\d{4})$	Social Security Number	f
5	^\\D?(\\d{3})\\D?\\D?(\\d{3})\\D?(\\d{4})$	Phone Number	f
7	^[0-9]{5}([- /]?[0-9]{4})?$	Zip Code Long	f
10	^\\d{5}$	Zip Code Short	f
3	^([0-9]{4}|[0-9]{2})[./-]([0]?[1-9]|[1][0-2])[./-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$	Date\n	f
9	^[a-zA-Z0-9]+(?:[ _.-][a-zA-Z0-9]+)*$	Letters,Numbers and Spaces	f
11	^([0-9]{4}|[0-9]{2})[./-]([0]?[1-9]|[1][0-2])[./-]([0]?[1-9]|[1|2][0-9]|[3][0|1])$	Time	f
\.


--
-- Data for Name: related_entity_type_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY related_entity_type_base (related_entity_type_id, attribute_id, child_entity_type_id, collapse, mod_user) FROM stdin;
\.


--
-- Data for Name: related_fieldtype_regex_base; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY related_fieldtype_regex_base (id, fieldtype_id, regex_id) FROM stdin;
2	2	1
3	6	4
4	7	5
5	8	7
6	9	7
7	10	8
8	11	3
9	13	6
10	1	9
11	2	9
12	17	2
1	1	9
13	12	11
14	3	9
15	4	9
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: client_template; Owner: -
--

COPY users (username, password, enabled) FROM stdin;
\.


SET search_path = public, pg_catalog;

--
-- Data for Name: att; Type: TABLE DATA; Schema: public; Owner: -
--

COPY att (attribute_value_id, attribute_id, entity_id, value_varchar, value_long, value_date, value_text, mod_user, search_index) FROM stdin;
53	125	19	brent	\N	\N	\N	jbent	'brent':1
\.


--
-- Data for Name: audit_action; Type: TABLE DATA; Schema: public; Owner: -
--

COPY audit_action (id, action) FROM stdin;
1	Create
2	Updated
3	Deleted
\.


--
-- Data for Name: audit_base; Type: TABLE DATA; Schema: public; Owner: -
--

COPY audit_base (audit_id, date, mod_user, audit_action) FROM stdin;
\.


--
-- Data for Name: client_info; Type: TABLE DATA; Schema: public; Owner: -
--

COPY client_info (id, client_name, schema_name) FROM stdin;
3092	testclient	testclient
3093	testclient	testclient
3094	testclient	testclient
3095	testclient	testclient
3096	testclient	testclient
3097	testclient	testclient
3098	testclient	testclient
3099	testclient	testclient
3100	testclient	testclient
3101	testclient	testclient
3102	testclient	testclient
3103	testclient	testclient
3104	testclient	testclient
3105	testclient	testclient
3106	testclient	testclient
3107	testclient	testclient
3108	testclient	testclient
3109	testclient	testclient
3110	testclient	testclient
3111	testclient	testclient
3112	jbrent	jbrent
3113	testclient	testclient
3114	testclient	testclient
3115	testclient	testclient
3116	testclient	testclient
3117	testclient	testclient
3118	testclient	testclient
3119	testclient	testclient
3120	testclient	testclient
3121	testclient	testclient
3122	testclient	testclient
3123	testclient	testclient
3124	testclient	testclient
3125	testclient	testclient
3126	testclient	testclient
3127	testclient	testclient
3128	testclient	testclient
3129	testclient	testclient
3130	testclient	testclient
3131	testclient	testclient
3132	testclient	testclient
3133	testclient	testclient
3134	testclient	testclient
3135	testclient	testclient
3136	testclient	testclient
3137	jbrent	jbrent
3138	testclient	testclient
3139	testclient	testclient
3140	testclient	testclient
3141	testclient	testclient
3142	testclient	testclient
3143	testclient	testclient
3144	testclient	testclient
3145	testclient	testclient
3146	testclient	testclient
3147	testclient	testclient
3148	testclient	testclient
3149	testclient	testclient
3150	testclient	testclient
3151	testclient	testclient
3152	testclient	testclient
3153	testclient	testclient
3154	testclient	testclient
3155	testclient	testclient
3156	testclient	testclient
3157	testclient	testclient
3158	testclient	testclient
3159	testclient	testclient
3160	testclient	testclient
3161	testclient	testclient
3162	jbrent	jbrent
3163	testclient	testclient
3164	testclient	testclient
3165	testclient	testclient
3166	testclient	testclient
3167	testclient	testclient
3168	testclient	testclient
3169	testclient	testclient
3170	testclient	testclient
3171	testclient	testclient
3172	testclient	testclient
3173	testclient	testclient
3174	testclient	testclient
3175	testclient	testclient
3176	testclient	testclient
3177	testclient	testclient
3178	testclient	testclient
3179	testclient	testclient
3180	testclient	testclient
3181	testclient	testclient
3182	testclient	testclient
3183	testclient	testclient
3184	testclient	testclient
3185	testclient	testclient
3186	testclient	testclient
3187	testclient	testclient
3188	testclient	testclient
3189	testclient	testclient
3190	testclient	testclient
3191	testclient	testclient
3192	testclient	testclient
3193	testclient	testclient
3194	testclient	testclient
3195	testclient	testclient
3196	testclient	testclient
3197	testclient	testclient
3198	testclient	testclient
3199	testclient	testclient
3200	jbrent	jbrent
3201	testclient	testclient
3202	testclient	testclient
3203	testclient	testclient
3204	testclient	testclient
3205	testclient	testclient
3206	testclient	testclient
3207	testclient	testclient
3208	testclient	testclient
3209	testclient	testclient
3210	testclient	testclient
3211	testclient	testclient
3212	testclient	testclient
3213	testclient	testclient
3214	testclient	testclient
3215	testclient	testclient
3216	testclient	testclient
3217	testclient	testclient
3218	testclient	testclient
3219	testclient	testclient
3220	testclient	testclient
3221	testclient	testclient
3222	testclient	testclient
3223	testclient	testclient
3224	testclient	testclient
3225	testclient	testclient
3226	testclient	testclient
3227	testclient	testclient
3228	jbrent	jbrent
3229	testclient	testclient
3230	testclient	testclient
3231	testclient	testclient
3232	testclient	testclient
3233	testclient	testclient
3234	testclient	testclient
3235	testclient	testclient
3236	testclient	testclient
3237	testclient	testclient
3238	testclient	testclient
3239	testclient	testclient
3240	testclient	testclient
3241	testclient	testclient
3242	testclient	testclient
3243	testclient	testclient
3244	testclient	testclient
3245	testclient	testclient
3246	testclient	testclient
3247	testclient	testclient
3248	testclient	testclient
3249	testclient	testclient
3250	testclient	testclient
3251	testclient	testclient
3252	testclient	testclient
3253	testclient	testclient
3254	testclient	testclient
3255	testclient	testclient
3256	testuser	testuser
3257	testuser	testuser
3258	testuser	testuser
3259	testclient	testclient
3260	testclient	testclient
3261	testclient	testclient
3262	testclient	testclient
3263	testclient	testclient
3264	testclient	testclient
3265	testclient	testclient
3266	testclient	testclient
3267	testclient	testclient
3268	testclient	testclient
3269	testclient	testclient
3270	testclient	testclient
3271	testclient	testclient
3272	testclient	testclient
3273	testclient	testclient
3274	testclient	testclient
3275	testclient	testclient
3276	testclient	testclient
3277	testclient	testclient
3278	testclient	testclient
3279	testclient	testclient
3280	testclient	testclient
3281	testclient	testclient
3282	testclient	testclient
3283	testclient	testclient
3284	testclient	testclient
3285	testclient	testclient
3286	testclient	testclient
3287	testclient	testclient
3288	testclient	testclient
3289	testclient	testclient
3290	testclient	testclient
3291	testclient	testclient
3292	testclient	testclient
3293	testclient	testclient
3294	jbrent	jbrent
3295	testclient	testclient
3296	testclient	testclient
3297	testclient	testclient
3298	testclient	testclient
3299	testclient	testclient
3300	testclient	testclient
3301	testclient	testclient
3302	testclient	testclient
3303	testclient	testclient
3304	testclient	testclient
3305	testclient	testclient
3306	testclient	testclient
3307	testclient	testclient
3308	testclient	testclient
3309	testclient	testclient
3310	testclient	testclient
3311	testclient	testclient
3312	testclient	testclient
3313	testclient	testclient
3314	testclient	testclient
3315	testclient	testclient
3316	testclient	testclient
3317	testclient	testclient
3318	testclient	testclient
3319	testclient	testclient
3320	testclient	testclient
3321	testclient	testclient
3322	testclient	testclient
3323	testclient	testclient
3324	testclient	testclient
3325	testclient	testclient
3326	testclient	testclient
3327	testuser	testuser
3328	testclient	testclient
3329	testclient	testclient
3330	testclient	testclient
3331	testclient	testclient
3332	testclient	testclient
3333	jbent	jbent
3341	test	test
3342	testclient	testclient
3343	testclient	testclient
3344	testclient	testclient
3345	testclient	testclient
3346	testclient	testclient
3347	testclient	testclient
3348	testclient	testclient
3349	testclient	testclient
3350	testclient	testclient
3351	testclient	testclient
3352	testclient	testclient
3353	testclient	testclient
3354	jbrent	jbrent
3355	testclient	testclient
3356	testclient	testclient
3357	testclient	testclient
3358	testclient	testclient
3359	testclient	testclient
3360	testclient	testclient
3361	testclient	testclient
3362	testclient	testclient
3363	testclient	testclient
3364	testclient	testclient
3365	testclient	testclient
3366	testclient	testclient
3367	testclient	testclient
3368	testclient	testclient
3369	testclient	testclient
3370	testclient	testclient
3371	testclient	testclient
3372	testclient	testclient
3373	testclient	testclient
3374	testclient	testclient
3375	testclient	testclient
3376	testclient	testclient
3377	testclient	testclient
3378	testclient	testclient
3379	testclient	testclient
3380	testclient	testclient
3381	testclient	testclient
3382	testclient	testclient
3383	testclient	testclient
3384	testclient	testclient
3385	testclient	testclient
3386	testclient	testclient
3387	testclient	testclient
3388	testclient	testclient
3389	testclient	testclient
3390	testclient	testclient
3391	testclient	testclient
3392	testclient	testclient
3393	testclient	testclient
3394	testclient	testclient
3395	testclient	testclient
3396	test	test
3397	test1	test1
3398	testclient	testclient
3399	jbent	jbent
3400	jbent	jbent
3401	jbent	jbent
3402	jbent	jbent
3403	jbent	jbent
3404	jbent	jbent
3405	testclient	testclient
3406	testclient	testclient
3407	testclient	testclient
3408	testclient	testclient
3409	testclient	testclient
3410	testclient	testclient
3411	testclient	testclinet
3412	testclient	testclient
3413	testclient	testclient
3414	testclient	testclient
3415	testclient	testclient
3416	testclient	testclient
3417	testclient	testclient
3418	testclient	testclient
3419	testclient	testclient
3420	testclient	testclient
3421	testclient	testclient
3422	testclient	testclient
3423	testclient	testclient
3424	testclient	testclient
3425	testclient	testclient
3426	testclient	testclient
3427	testclient	testclient
3428	testclient	testclient
3429	testclient	testclient
3430	testclient	testclient
3431	testclient	testclient
3432	testclient	testclient
3433	testclient	testclient
3434	testclient	testclient
3435	testclient	testclient
3436	jbrent	jbrent
3437	testclient	testclient
3438	testclient	testclient
3439	testclient	testclient
3440	testclient	testclient
3441	testclient	testclient
3442	testclient	testclient
3443	testclient	testclient
3444	testclient	testclient
3445	testclient	testclient
3446	testclient	testclient
3447	testclient	testclient
3448	testclient	testclient
3449	testclient	testclient
3450	testclient	testclient
3451	testclient	testclient
3452	testclient	testclient
3453	testclient	testclient
3454	testclient	testclient
3455	testclient	testclient
3456	testclient	testclient
3457	testclient	testclient
3458	testclient	testclient
3459	testclient	testclient
3460	testclient	testclient
3461	jbrent	jbrent
3462	testclient	testclient
3463	testclient	testclient
3464	testclient	testclient
3465	testclient	testclient
3466	testclient	testclient
3467	testclient	testclient
3468	testclient	testclient
3469	testclient	testclient
3470	testclient	testclient
3471	testclient	testclient
3472	testclient	testclient
3473	testclient	testclient
3474	testclient	testclient
3475	testclient	testclient
3476	testclient	testclient
3477	testclient	testclient
3478	testclient	testclient
3479	testclient	testclient
3480	testclient	testclient
3481	testclient	testclient
3482	testclient	testclient
3483	testclient	testclient
3484	testclient	testclient
3485	testclient	testclient
3486	testclient	testclient
3487	testclient	testclient
3488	testclient	testclient
3489	testclient	testclient
3490	testclient	testclient
3491	testclient	testclinet
3492	testclient	testclinet
3493	testclient	testclient
3494	testclient	testclient
3495	testclient	testclinet
3496	testclient	testclinet
3497	testclient	testclinet
3498	testclient	testclinet
3499	testclient	testclient
3500	testclient	testclient
3501	testclient	testclient
3502	testclient	testclient
3503	testclient	testclient
3504	testclient	testclient
3505	testclient	testclinet
3506	testclient	testclinet
3507	testclient	testclient
3508	testclient	testclient
3509	testclient	testclient
3510	testclient	testclient
3511	testclient	testclient
3512	testclient	testclient
3513	testclient	testclient
3514	testclient	testclient
3515	testclient1	testclient1
3516	testclient	testclient
3517	testclient	testclient
3518	testclient	testclient
3519	testclient	testclient
3520	testclient	testclient
3521	testclient	testclient
3522	testclient	testclient
3523	testclient	testclient
3524	testclient	testclient
3525	testclient	testclient
3526	testclient	testclient
3527	testclient	testclient
3528	testclient	testclient
3529	testclient	testclient
3530	testclient	testclient
3531	testclient	testclient
3532	testclient	testclient
3533	testclient	testclient
3534	testclient	testclient
3535	testclient	testclient
3536	testclient	testclient
3537	testclient	testclient
3538	testclient	testclient
3539	testclient	testclient
3540	testclient	testclient
3541	testclient	testclient
3542	testclient	testclient
3543	testclient	testclient
3544	testclient	testclient
3545	testclient	testclient
3546	testclient	testclient
3547	testclient	testclient
3548	testclient	testclient
3549	testclient	testclient
3550	testclient	testclient
3551	testclient	testclient
3552	testclient	testclient
3553	testclient	testclient
3554	testclient	testclient
3555	testclient	testclient
3556	testclient	testclient
3557	testclient	testclient
3558	testclient	testclient
3559	testclient	testclient
3560	testclient	testclient
3561	testclient	testclient
3562	testclient	testclient
3563	testclient	testclient
3564	testclient	testclient
3565	testclient	testclient
3566	testclient	testclient
3567	testclient	testclient
3568	jbrent	jbrent
3569	testclient	testclient
3570	testclient	testclient
3571	testclient	testclient
3572	testclient	testclient
3573	testclient	testclient
3574	testclient	testclient
3575	testclient	testclient
3576	testclient	testclient
3577	testclient	testclient
3578	testclient	testclient
3579	testclient	testclient
3580	testclient	testclient
3581	testclient	testclient
3582	testclient	testclient
3583	testclient	testclient
3584	testclient	testclient
3585	testclient	testclient
3586	testclient	testclient
3587	testclient	testclient
3588	testclient	testclient
3589	testclient	testclient
3590	testclient	testclient
3591	testclient	testclient
3592	testclient	testclient
3593	testclient	testclient
3594	testclient	testclient
3595	testclient	testclient
3596	testclient	testclient
3597	testclient	testclient
3598	testclient	testclient
3599	testclient	testclient
3600	testclient	testclient
3601	testclient	testclient
3602	testclient	testclient
3603	testclient	testclient
3604	testclient	testclient
3605	testclient	testclient
3606	testclient	testclient
3607	testclient	testclient
3608	testclient	testclient
3609	testclient	testclient
3610	testclient	testclient
3611	testclient	testclient
3612	testclient	testclient
3613	testclient	testclient
3614	testclient	testclient
3615	testclient	testclient
3616	testclient	testclient
3617	testclient	testclient
3618	testclient	testclient
3619	testclient	testclient
3620	testclient	testclient
3621	testclient	testclient
3622	testclient	testclient
3623	testclient	testclient
3624	testclient	testclient
3625	testclient	testclient
3626	testclient	testclient
3627	testclient	testclient
3628	testclient	testclient
3629	testclient	testclient
3630	testclient	testclient
3631	testclient	testclient
3632	testclient	testclient
3633	testclient	testclient
3634	testclient	testclient
3635	testclient	testclient
3636	testclient	testclient
3637	testclient	testclient
3638	testclient	testclient
3639	testclient	testclient
3640	testclient	testclient
3641	testclient	testclient
3642	testclient	testclient
3643	jbrent	jbrent
3644	testclient	testclient
3645	testclient	testclient
3646	testclient	testclient
3647	testclient	testclient
3648	testclient	testclient
3649	testclient	testclient
3650	testclient	testclient
3651	testclient	testclient
3652	testclient	testclient
3653	testclient	testclient
3654	testclient	testclient
3655	testclient	testclient
3656	testclient	testclient
3657	testclient	testclient
3658	testclient	testclient
3659	testclient	testclient
3660	testclient	testclient
3661	testclient	testclient
3662	testclient	testclient
3663	testclient	testclient
3664	testclient	testclient
3665	testclient	testclient
3666	testclient	testclient
3667	testclient	testclient
3668	testclient	testclient
3669	testclient	testclient
3670	testclient	testclient
3671	testclient	testclient
3672	testclient	testclient
3673	testclient	testclient
3674	testclient	testclient
3675	testclient	testclient
3676	testclient	testclient
3677	testclient	testclient
3678	testclient	testclient
3679	testclient	testclient
3680	testclient	testclient
3681	testclient	testclient
3682	testclient	testclient
3683	jbrent	jbrent
3684	testclient	testclient
3685	testclient	testclient
3686	testclient	testclient
3687	testclient	testclient
3688	testclient	testclient
3689	testclient	testclient
3690	testclient	testclient
3691	testclient	testclient
3692	testclient	testclient
3693	testclient	testclient
3694	testclient	testclient
3695	testclient	testclient
3696	testclient	testclient
3697	testclient	testclient
3698	testclient	testclient
3699	testclient	testclient
3700	testclient	testclient
3701	testclient	testclient
3702	testclient	testclient
3703	testclient	testclient
3704	testclient	testclient
3705	testclient	testclient
3706	testclient	testclient
3707	testclient	testclient
3708	testclient	testclient
3709	testclient	testclient
3710	testclient	testclient
3711	testclient	testclient
3712	testclient	testclient
3713	testclient	testclient
3714	testclient	testclient
3715	testclient	testclient
3716	testclient	testclient
3717	testclient	testclient
3718	testclient	testclient
3719	testclient	testclient
3720	testclient	testclient
3721	testclient	testclient
3722	testclient	testclient
3723	testclient	testclient
3724	testclient	testclient
3725	testclient	testclient
3726	testclient	testclient
3727	testclient	testclient
3728	testclient	testclient
3729	testclient	testclient
3730	testclient	testclient
3731	testclient	testclient
3732	testclient	testclient
3733	testclient	testclient
3734	testclient	testclient
3735	testclient	testclient
3736	testclient	testclient
3737	testclient	testclient
3738	testclient	testclient
3739	testclient	testclient
3740	testclient	testclient
3741	testclient	testclient
3742	testclient	testclient
3743	testclient	testclient
3744	testclient	testclient
3745	testclient	testclient
3746	testclient	testclient
3747	testclient	testclient
3748	testclient	testclient
3749	testclient	testclient
3750	testclient	testclient
3751	testclient	testclient
3752	testclient	testclient
3753	testclient	testclient
3754	testclient	testclient
3755	testclient	testclient
3756	testclient	testclient
3757	testclient	testclient
3758	testclient	testclient
3759	testclient	testclient
3760	testclient	testclient
3761	testclient	testclient
3762	testclient	testclient
3763	testclient	testclient
3764	testclient	testclient
3765	testclient	testclient
3766	testclient	testclient
3767	testclient	testclient
3768	testclient	testclient
3769	jbrent	jbrent
3770	testclient	testclient
3771	testclient	testclient
3772	testclient	testclient
3773	testclient	testclient
3774	testclient	testclient
3775	testclient	testclient
3776	testclient	testclient
3777	testclient	testclient
3778	testclient	testclient
3779	testclient	testclient
3780	testclient	testclient
3781	testclient	testclient
3782	testclient	testclient
3783	testclient	testclient
3784	testclient	testclient
3785	testclient	testclient
3786	testclient	testclient
3787	testclient	testclient
3788	testclient	testclient
3789	testclient	testclient
3790	testclient	testclient
3791	testclient	testclient
3792	testclient	testclient
3793	testclient	testclient
3794	testclient	testclient
3795	testclient	testclient
3796	testclient	testclient
3797	testclient	testclient
3798	testclient	testclient
3799	testclient	testclient
3800	testclient	testclient
3801	testclient	testclient
3802	testclient	testclient
3803	testclient	testclient
3804	testclient	testclient
3805	testclient	testclient
3806	testclient	testclient
3807	testclient	testclient
3808	testclient	testclient
3809	testclient	testclient
3810	testclient	testclient
3811	testclient	testclient
3812	testclient	testclient
3813	testclient	testclient
3814	testclient	testclient
3815	testclient	testclient
3816	testclient	testclient
3817	testclient	testclient
3818	testclient	testclient
3819	testclient	testclient
3820	testclient	testclient
3821	testclient	testclient
3822	testclient	testclient
3823	testclient	testclient
3824	jbrent	jbrent
3825	testclient	testclient
3826	testclient	testclient
3827	testclient	testclient
3828	testclient	testclient
3829	testclient	testclient
3830	testclient	testclient
3831	testclient	testclient
3832	testclient	testclient
3833	testclient	testclient
3834	testclient	testclient
3835	testclient	testclient
3836	testclient	testclient
3837	testclient	testclient
3838	testclient	testclient
3839	testclient	testclient
3840	testclient	testclient
3841	testclient	testclient
3842	testclient	testclient
3843	testclient	testclient
3844	testclient	testclient
3845	testclient	testclient
3846	testclient	testclient
3847	testclient	testclient
3848	testclient	testclient
3849	testclient	testclient
3850	testclient	testclient
3851	testclient	testclient
3852	testclient	testclient
3853	testclient	testclient
3854	testclient	testclient
3855	testclient	testclient
3856	testclient	testclient
3857	testclient	testclient
3858	testclient	testclient
3859	testclient	testclient
3860	testclient	testclient
3861	testclient	testclient
3862	testclient	testclient
3863	testclient	testclient
3864	testclient	testclient
3865	testclient	testclient
3866	testclient	testclient
3867	testclient	testclient
3868	testclient	testclient
3869	testclient	testclient
3870	testclient	testclient
3871	testclient	testclient
3872	testclient	testclient
3873	testclient	testclient
3874	testclient	testclient
3875	testclient	testclient
3876	testclient	testclient
3877	testclient	testclient
3878	testclient	testclient
3879	testclient	testclient
3880	testclient	testclient
3881	testclient	testclient
3882	testclient	testclient
3883	testclient	testclient
3884	testclient	testclient
3885	testclient	testclient
3886	jbrent	jbrent
3887	testclient	testclient
3888	testclient	testclient
3889	testclient	testclient
3890	testclient	testclient
3891	testclient	testclient
3892	testclient	testclient
3893	testclient	testclient
3894	testclient	testclient
3895	testclient	testclient
3896	testclient	testclient
3897	testclient	testclient
3898	testclient	testclient
3899	testclient	testclient
3900	testclient	testclient
3901	testclient	testclient
3902	testclient	testclient
3903	testclient	testclient
3904	testclient	testclient
3905	testclient	testclient
3906	testclient	testclient
3907	testclient	testclient
3908	testclient	testclient
3909	testclient	testclient
3910	testclient	testclient
3911	testclient	testclient
3912	testclient	testclient
3913	testclient	testclient
3914	testclient	testclient
3915	testclient	testclient
3916	testclient	testclient
3917	testclient	testclient
3918	testclient	testclient
3919	testclient	testclient
3920	testclient	testclient
3921	testclient	testclient
3922	testclient	testclient
3923	testclient	testclient
3924	testclient	testclient
3925	testclient	testclient
3926	testclient	testclient
3927	testclient	testclient
3928	testclient	testclient
3929	testclient	testclient
3930	testclient	testclient
3931	testclient	testclient
3932	testclient	testclient
3933	testclient	testclient
3934	testclient	testclient
3935	testclient	testclient
3936	testclient	testclient
3937	testclient	testclient
3938	testclient	testclient
3939	testclient	testclient
3940	testclient	testclient
3941	testclient	testclient
3942	testclient	testclient
3943	testclient	testclient
3944	testclient	testclient
3945	testclient	testclient
3946	testclient	testclient
3947	testclient	testclient
3948	testclient	testclient
3949	testclient	testclient
3950	testclient	testclient
3951	testclient	testclient
3952	testclient	testclient
3953	testclient	testclient
3954	jbrent	jbrent
3955	testclient	testclient
3956	testclient	testclient
3957	testclient	testclient
3958	testclient	testclient
3959	testclient	testclient
3960	testclient	testclient
3961	testclient	testclient
3962	testclient	testclient
3963	testclient	testclient
3964	testclient	testclient
3965	testclient	testclient
3966	testclient	testclient
3967	testclient	testclient
3968	testclient	testclient
3969	testclient	testclient
3970	testclient	testclient
3971	testclient	testclient
3972	testclient	testclient
3973	testclient	testclient
3974	testclient	testclient
3975	testclient	testclient
3976	testclient	testclient
3977	testclient	testclient
3978	testclient	testclient
3979	testclient	testclient
3980	testclient	testclient
3981	testclient	testclient
3982	testclient	testclient
3983	testclient	testclient
3984	testclient	testclient
3985	testclient	testclient
3986	testclient	testclient
3987	testclient	testclient
3988	testclient	testclient
3989	testclient	testclient
3990	testclient	testclient
3991	testclient	testclient
3992	testclient	testclient
3993	testclient	testclient
3994	testclient	testclient
3995	testclient	testclient
3996	testclient	testclient
3997	testclient	testclient
3998	testclient	testclient
3999	testclient	testclient
4000	testclient	testclient
4001	testclient	testclient
4002	testclient	testclient
4003	testclient	testclient
4004	testclient	testclient
4005	jbrent	jbrent
4006	testclient	testclient
4007	testclient	testclient
4008	testclient	testclient
4009	testclient	testclient
4010	testclient	testclient
4011	testclient	testclient
4012	testclient	testclient
4013	testclient	testclient
4014	testclient	testclient
4015	testclient	testclient
4016	testclient	testclient
4017	testclient	testclient
4018	testclient	testclient
4019	testclient	testclient
4020	testclient	testclient
4021	testclient	testclient
4022	testclient	testclient
4023	testclient	testclient
4024	testclient	testclient
4025	testclient	testclient
4026	testclient	testclient
4027	testclient	testclient
4028	testclient	testclient
4029	testclient	testclient
4030	testclient	testclient
4031	testclient	testclient
4032	testclient	testclient
4033	testclient	testclient
4034	testclient	testclient
4035	testclient	testclient
4036	testclient	testclient
4037	testclient	testclient
4038	jbrent	jbrent
4039	testclient	testclient
4040	testclient	testclient
4041	testclient	testclient
4042	testclient	testclient
4043	testclient	testclient
4044	testclient	testclient
4045	testclient	testclient
4046	testclient	testclient
4047	testclient	testclient
4048	testclient	testclient
4049	testclient	testclient
4050	testclient	testclient
4051	testclient	testclient
4052	testclient	testclient
4053	testclient	testclient
4054	testclient	testclient
4055	testclient	testclient
4056	testclient	testclient
4057	testclient	testclient
4058	testclient	testclient
4059	testclient	testclient
4060	testclient	testclient
4061	testclient	testclient
4062	testclient	testclient
4063	testclient	testclient
4064	testclient	testclient
4065	testclient	testclient
4066	testclient	testclient
4067	testclient	testclient
4068	testclient	testclient
4069	testclient	testclient
4070	testclient	testclient
4071	testclient	testclient
4072	testclient	testclient
4073	testclient	testclient
4074	testclient	testclient
4075	testclient	testclient
4076	testclient	testclient
4077	testclient	testclient
4078	testclient	testclient
4079	testclient	testclient
4080	testclient	testclient
4081	testclient	testclient
4082	testclient	testclient
4083	testclient	testclient
4084	testclient	testclient
4085	testclient	testclient
4086	testclient	testclient
4087	testclient	testclient
4088	testclient	testclient
4089	testclient	testclient
4090	testclient	testclient
4091	testclient	testclient
4092	testclient	testclient
4093	testclient	testclient
4094	testclient	testclient
4095	testclient	testclient
4096	testclient	testclient
4097	jbrent	jbrent
4098	testclient	testclient
4099	testclient	testclient
4100	testclient	testclient
4101	testclient	testclient
4102	testclient	testclient
4103	testclient	testclient
4104	testclient	testclient
4105	testclient	testclient
4106	testclient	testclient
4107	testclient	testclient
4108	testclient	testclient
4109	testclient	testclient
4110	testclient	testclient
4111	testclient	testclient
4112	testclient	testclient
4113	testclient	testclient
4114	testclient	testclient
4115	testclient	testclient
4116	testclient	testclient
4117	testclient	testclient
4118	testclient	testclient
4119	testclient	testclient
4120	testclient	testclient
4121	testclient	testclient
4122	testclient	testclient
4123	testclient	testclient
4124	testclient	testclient
4125	testclient	testclient
4126	testclient	testclient
4127	testclient	testclient
4128	testclient	testclient
4129	testclient	testclient
4130	testclient	testclient
4131	testclient	testclient
4132	testclient	testclient
4133	testclient	testclient
4134	testclient	testclient
4135	testclient	testclient
4136	testclient	testclient
4137	testclient	testclient
4138	testclient	testclient
4139	testclient	testclient
4140	testclient	testclient
4141	testclient	testclient
4142	testclient	testclient
4143	testclient	testclient
4144	testclient	testclient
4145	jbrent	jbrent
4146	testclient	testclient
4147	testclient	testclient
4148	testclient	testclient
4149	testclient	testclient
4150	testclient	testclient
4151	testclient	testclient
4152	testclient	testclient
4153	testclient	testclient
4154	testclient	testclient
4155	testclient	testclient
4156	testclient	testclient
4157	testclient	testclient
4158	testclient	testclient
4159	testclient	testclient
4160	testclient	testclient
4161	testclient	testclient
4162	testclient	testclient
4163	testclient	testclient
4164	testclient	testclient
4165	testclient	testclient
4166	testclient	testclient
4167	testclient	testclient
4168	testclient	testclient
4169	testclient	testclient
\.


--
-- Data for Name: ent_name; Type: TABLE DATA; Schema: public; Owner: -
--

COPY ent_name (entity_type_id, entity_type_name, mod_user, template, edit_template, lock_mode, deletable, treeable) FROM stdin;
38	People	jbent	\N	\N	f	t	f
\.


SET search_path = client_template, pg_catalog;

--
-- Name: acl_class_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_class
    ADD CONSTRAINT acl_class_pkey PRIMARY KEY (id);


--
-- Name: acl_entry_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT acl_entry_pkey PRIMARY KEY (id);


--
-- Name: acl_object_identity_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT acl_object_identity_pkey PRIMARY KEY (id);


--
-- Name: acl_sid_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_sid
    ADD CONSTRAINT acl_sid_pkey PRIMARY KEY (id);


--
-- Name: attribute_datatype_datatype_name_key; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_datatype_base
    ADD CONSTRAINT attribute_datatype_datatype_name_key UNIQUE (datatype_name);


--
-- Name: attribute_datatype_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_datatype_base
    ADD CONSTRAINT attribute_datatype_pkey PRIMARY KEY (datatype_id);


--
-- Name: attribute_fieldtype_fieldtype_name_key; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_fieldtype_base
    ADD CONSTRAINT attribute_fieldtype_fieldtype_name_key UNIQUE (fieldtype_name);


--
-- Name: attribute_fieldtype_option_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_fieldtype_option_base
    ADD CONSTRAINT attribute_fieldtype_option_base_pkey PRIMARY KEY (fieldtype_option_id);


--
-- Name: attribute_fieldtype_option_value_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_fieldtype_option_value_base
    ADD CONSTRAINT attribute_fieldtype_option_value_base_pkey PRIMARY KEY (option_value_id);


--
-- Name: attribute_fieldtype_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_fieldtype_base
    ADD CONSTRAINT attribute_fieldtype_pkey PRIMARY KEY (fieldtype_id);


--
-- Name: attribute_value_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_value_base
    ADD CONSTRAINT attribute_value_base_pkey PRIMARY KEY (attribute_value_id);


--
-- Name: attribute_value_file_storage_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_value_file_storage_base
    ADD CONSTRAINT attribute_value_file_storage_base_pkey PRIMARY KEY (attribute_value_file_storage_id);


--
-- Name: attribute_value_storage_base_entity_id_key; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_value_storage_base
    ADD CONSTRAINT attribute_value_storage_base_entity_id_key UNIQUE (entity_id, attribute_id,attribute_value_file_storage_id);


--
-- Name: attribute_value_storage_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_value_storage_base
    ADD CONSTRAINT attribute_value_storage_base_pkey PRIMARY KEY (attribute_value_id);


--
-- Name: audit_attribute_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_attribute_base
    ADD CONSTRAINT audit_attribute_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_attribute_value_file_storage_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_attribute_value_file_storage_base
    ADD CONSTRAINT audit_attribute_value_file_storage_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_attribute_value_storage_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_attribute_value_storage_base
    ADD CONSTRAINT audit_attribute_value_storage_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_choice_attribute_detail_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_choice_attribute_detail_base
    ADD CONSTRAINT audit_choice_attribute_detail_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_entity_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_entity_base
    ADD CONSTRAINT audit_entity_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_entity_type_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_entity_type_base
    ADD CONSTRAINT audit_entity_type_base_pkey PRIMARY KEY (audit_id);


--
-- Name: audit_related_entity_type_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_related_entity_type_base
    ADD CONSTRAINT audit_related_entity_type_base_pkey PRIMARY KEY (audit_id);


--
-- Name: box_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY box_base
    ADD CONSTRAINT box_base_pkey PRIMARY KEY (box_id);


--
-- Name: box_base_user_entity_id_key; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY box_base
    ADD CONSTRAINT box_base_user_entity_id_key UNIQUE (user_entity_id);


--
-- Name: choice_attribute_detail_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY choice_attribute_detail_base
    ADD CONSTRAINT choice_attribute_detail_base_pkey PRIMARY KEY (choice_attribute_id);


--
-- Name: entity_attribute_base_entity_id_key1; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT entity_attribute_base_entity_id_key1 UNIQUE (entity_type_id, attribute_name);


--
-- Name: entity_attribute_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT entity_attribute_base_pkey PRIMARY KEY (attribute_id);


--
-- Name: entity_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_base
    ADD CONSTRAINT entity_pkey PRIMARY KEY (entity_id);


--
-- Name: entity_type_entity_type_name_key; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_type_base
    ADD CONSTRAINT entity_type_entity_type_name_key UNIQUE (entity_type_name);


--
-- Name: entity_type_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY entity_type_base
    ADD CONSTRAINT entity_type_pkey PRIMARY KEY (entity_type_id);


--
-- Name: id; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY related_fieldtype_regex_base
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- Name: import_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY import_base
    ADD CONSTRAINT import_base_pkey PRIMARY KEY (import_id);


--
-- Name: import_failed_row_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY import_failed_row_base
    ADD CONSTRAINT import_failed_row_base_pkey PRIMARY KEY (id);


--
-- Name: persistent_logins_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY persistent_logins
    ADD CONSTRAINT persistent_logins_pkey PRIMARY KEY (series);


--
-- Name: regex; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY regex_base
    ADD CONSTRAINT regex PRIMARY KEY (regex_id);


--
-- Name: related_entity_type_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY related_entity_type_base
    ADD CONSTRAINT related_entity_type_base_pkey PRIMARY KEY (related_entity_type_id);


--
-- Name: schema_audit_base_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_summary_base
    ADD CONSTRAINT schema_audit_base_pkey PRIMARY KEY (audit_id);


--
-- Name: unique_uk_1; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_sid
    ADD CONSTRAINT unique_uk_1 UNIQUE (sid, principal);


--
-- Name: unique_uk_2; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_class
    ADD CONSTRAINT unique_uk_2 UNIQUE (class);


--
-- Name: unique_uk_3; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT unique_uk_3 UNIQUE (object_id_class, object_id_identity);


--
-- Name: unique_uk_4; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT unique_uk_4 UNIQUE (acl_object_identity, ace_order);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: client_template; Owner: -; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


SET search_path = public, pg_catalog;

--
-- Name: audit_action_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_action
    ADD CONSTRAINT audit_action_pkey PRIMARY KEY (id);


--
-- Name: audit_base_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY audit_base
    ADD CONSTRAINT audit_base_pkey PRIMARY KEY (audit_id);


SET search_path = client_template, pg_catalog;

--
-- Name: FK_audit_entity_user; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX "FK_audit_entity_user" ON audit_entity_base USING btree (mod_user);


--
-- Name: fki_; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX fki_ ON attribute_base USING btree (attribute_datatype_id);


--
-- Name: fki_attribute_fieldtype; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX fki_attribute_fieldtype ON attribute_base USING btree (attribute_fieldtype_id);


--
-- Name: fki_entity_type_user; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX fki_entity_type_user ON audit_entity_type_base USING btree (mod_user);


--
-- Name: ix_auth_username; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE UNIQUE INDEX ix_auth_username ON authorities USING btree (username, authority);


--
-- Name: search_index_idx; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX search_index_idx ON entity_base USING gin (search_index);


--
-- Name: storage_value_search_index_idx; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX storage_value_search_index_idx ON attribute_value_storage_base USING gin (search_index);


--
-- Name: textsearch_idx; Type: INDEX; Schema: client_template; Owner: -; Tablespace: 
--

CREATE INDEX textsearch_idx ON entity_base USING gin (search_index);


--
-- Name: audit_attribute_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_attribute_base AFTER INSERT OR DELETE OR UPDATE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE process_audit_attribute_base();


--
-- Name: audit_attribute_value_file_storage_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_attribute_value_file_storage_base AFTER INSERT OR DELETE OR UPDATE ON attribute_value_file_storage_base FOR EACH ROW EXECUTE PROCEDURE process_audit_attribute_value_file_storage_base();


--
-- Name: audit_attribute_value_storage_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_attribute_value_storage_base AFTER INSERT OR DELETE OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE process_audit_attribute_value_storage_base();


--
-- Name: audit_choice_attribute_detail_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_choice_attribute_detail_base AFTER INSERT OR DELETE OR UPDATE ON choice_attribute_detail_base FOR EACH ROW EXECUTE PROCEDURE process_audit_choice_attribute_detail_base();


--
-- Name: audit_entity_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_entity_base AFTER INSERT OR DELETE OR UPDATE ON entity_base FOR EACH ROW EXECUTE PROCEDURE process_audit_entity_base();


--
-- Name: audit_entity_type_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_entity_type_base AFTER INSERT OR DELETE OR UPDATE ON entity_type_base FOR EACH ROW EXECUTE PROCEDURE process_audit_entity_type_base();


--
-- Name: audit_related_entity_type_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER audit_related_entity_type_base AFTER INSERT OR DELETE OR UPDATE ON related_entity_type_base FOR EACH ROW EXECUTE PROCEDURE process_audit_related_entity_type_base();


--
-- Name: check_delete_attribute_order_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_delete_attribute_order_base AFTER DELETE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE check_attribute_order();


--
-- Name: check_dup_filename; Type: TRIGGER; Schema: client_template; Owner: -
--

--CREATE TRIGGER check_dup_filename BEFORE INSERT OR UPDATE ON attribute_value_file_storage_base FOR EACH ROW EXECUTE PROCEDURE check_filename();


--
-- Name: check_entity_type; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_entity_type BEFORE INSERT OR UPDATE ON entity_base FOR EACH ROW EXECUTE PROCEDURE check_entity_type();


--
-- Name: check_insert_attribute_order_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_insert_attribute_order_base BEFORE INSERT ON attribute_base FOR EACH ROW EXECUTE PROCEDURE check_attribute_order();


--
-- Name: check_pwd_index; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_pwd_index BEFORE INSERT OR UPDATE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE unset_index_ftypepwd();


--
-- Name: check_value_data; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_value_data BEFORE INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE check_attribute_value_data_base();


--
-- Name: chk_dup_usr; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER chk_dup_usr BEFORE INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE check_dup_user_name();


--
-- Name: chk_entity_valid; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER chk_entity_valid AFTER INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE check_entity_valid();


--
-- Name: del_custom_reg; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER del_custom_reg AFTER DELETE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE delete_custom_regex();


--
-- Name: exists_attribute_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_attribute_mod_user BEFORE INSERT OR UPDATE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();

ALTER TABLE attribute_base DISABLE TRIGGER exists_attribute_mod_user;


--
-- Name: exists_attribute_value_file_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_attribute_value_file_mod_user BEFORE INSERT OR UPDATE ON attribute_value_file_storage_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();

ALTER TABLE attribute_value_file_storage_base DISABLE TRIGGER exists_attribute_value_file_mod_user;


--
-- Name: exists_attribute_value_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_attribute_value_mod_user BEFORE INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();

ALTER TABLE attribute_value_storage_base DISABLE TRIGGER exists_attribute_value_mod_user;


--
-- Name: exists_choice_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_choice_mod_user BEFORE INSERT OR UPDATE ON choice_attribute_detail_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();


--
-- Name: exists_entity_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_entity_mod_user BEFORE INSERT OR UPDATE ON entity_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();

ALTER TABLE entity_base DISABLE TRIGGER exists_entity_mod_user;


--
-- Name: exists_entity_type_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_entity_type_mod_user BEFORE INSERT OR UPDATE ON entity_type_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();

ALTER TABLE entity_type_base DISABLE TRIGGER exists_entity_type_mod_user;


--
-- Name: exists_related_mod_user; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_related_mod_user BEFORE INSERT OR UPDATE ON related_entity_type_base FOR EACH ROW EXECUTE PROCEDURE check_user_base();


--
-- Name: exists_role; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_role BEFORE INSERT OR UPDATE ON authorities FOR EACH ROW EXECUTE PROCEDURE check_authority();


--
-- Name: exists_user_name; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER exists_user_name BEFORE INSERT OR UPDATE ON authorities FOR EACH ROW EXECUTE PROCEDURE check_user_name();


--
-- Name: import_base; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER import_base BEFORE INSERT ON import_base FOR EACH ROW EXECUTE PROCEDURE process_import_base();


--
-- Name: map_fieldtype_to_datatype; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER map_fieldtype_to_datatype BEFORE INSERT OR UPDATE ON attribute_base FOR EACH ROW EXECUTE PROCEDURE maping_fieldtypes_to_datatypes();


--
-- Name: text_search_on_storage_data; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER text_search_on_storage_data BEFORE INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE storage_text_search_build();


--
-- Name: att_id; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY choice_attribute_detail_base
    ADD CONSTRAINT att_id FOREIGN KEY (attribute_id) REFERENCES attribute_base(attribute_id) ON DELETE CASCADE;


--
-- Name: attribute_base_attribute_datatype_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT attribute_base_attribute_datatype_id_fkey FOREIGN KEY (attribute_datatype_id) REFERENCES attribute_datatype_base(datatype_id) DEFERRABLE;


--
-- Name: attribute_base_regex_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT attribute_base_regex_id_fkey FOREIGN KEY (regex_id) REFERENCES regex_base(regex_id);


--
-- Name: attribute_fieldtype_id; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_fieldtype_option_base
    ADD CONSTRAINT attribute_fieldtype_id FOREIGN KEY (attribute_fieldtype_id) REFERENCES attribute_fieldtype_base(fieldtype_id);


--
-- Name: attribute_fieldtype_option_va_attribute_fieldtype_option_i_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_fieldtype_option_value_base
    ADD CONSTRAINT attribute_fieldtype_option_va_attribute_fieldtype_option_i_fkey FOREIGN KEY (attribute_fieldtype_option_id) REFERENCES attribute_fieldtype_option_base(fieldtype_option_id);


--
-- Name: attribute_fieldtype_option_value_base_attribute_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_fieldtype_option_value_base
    ADD CONSTRAINT attribute_fieldtype_option_value_base_attribute_id_fkey FOREIGN KEY (attribute_id) REFERENCES attribute_base(attribute_id) ON DELETE CASCADE;


--
-- Name: attribute_id_fk; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_value_storage_base
    ADD CONSTRAINT attribute_id_fk FOREIGN KEY (attribute_id) REFERENCES attribute_base(attribute_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: attribute_value_base_entity_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_value_base
    ADD CONSTRAINT attribute_value_base_entity_id_fkey FOREIGN KEY (entity_id) REFERENCES entity_base(entity_id) ON DELETE CASCADE;


--
-- Name: attribute_value_file_storage_base_attribute_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_value_file_storage_base
    ADD CONSTRAINT attribute_value_file_storage_base_attribute_id_fkey FOREIGN KEY (attribute_id) REFERENCES attribute_base(attribute_id) ON DELETE CASCADE;


--
-- Name: attribute_value_file_storage_base_entity_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_value_file_storage_base
    ADD CONSTRAINT attribute_value_file_storage_base_entity_id_fkey FOREIGN KEY (entity_id) REFERENCES entity_base(entity_id) ON DELETE CASCADE;


--
-- Name: attt_id; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY related_entity_type_base
    ADD CONSTRAINT attt_id FOREIGN KEY (attribute_id) REFERENCES attribute_base(attribute_id) ON DELETE CASCADE;


--
-- Name: box_base_entity_base_box_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY box_base_entity_base
    ADD CONSTRAINT box_base_entity_base_box_id_fkey FOREIGN KEY (box_id) REFERENCES box_base(box_id);


--
-- Name: entity_attribute_base_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT entity_attribute_base_entity_type_id_fkey FOREIGN KEY (entity_type_id) REFERENCES entity_type_base(entity_type_id) ON DELETE CASCADE;


--
-- Name: entity_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY entity_base
    ADD CONSTRAINT entity_entity_type_id_fkey FOREIGN KEY (entity_type_id) REFERENCES entity_type_base(entity_type_id) ON DELETE CASCADE;


--
-- Name: fk_attribute_fieldtype; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_base
    ADD CONSTRAINT fk_attribute_fieldtype FOREIGN KEY (attribute_fieldtype_id) REFERENCES attribute_fieldtype_base(fieldtype_id);


--
-- Name: foreign_fk_1; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_1 FOREIGN KEY (parent_object) REFERENCES acl_object_identity(id);


--
-- Name: foreign_fk_2; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_2 FOREIGN KEY (object_id_class) REFERENCES acl_class(id);


--
-- Name: foreign_fk_3; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY acl_object_identity
    ADD CONSTRAINT foreign_fk_3 FOREIGN KEY (owner_sid) REFERENCES acl_sid(id);


--
-- Name: foreign_fk_4; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT foreign_fk_4 FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);


--
-- Name: foreign_fk_5; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY acl_entry
    ADD CONSTRAINT foreign_fk_5 FOREIGN KEY (sid) REFERENCES acl_sid(id);


--
-- Name: related_entity_type_base_child_entity_type_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY related_entity_type_base
    ADD CONSTRAINT related_entity_type_base_child_entity_type_id_fkey FOREIGN KEY (child_entity_type_id) REFERENCES entity_type_base(entity_type_id) DEFERRABLE;


--
-- Name: varchar_attribute_value_entity_id_fkey; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE ONLY attribute_value_storage_base
    ADD CONSTRAINT varchar_attribute_value_entity_id_fkey FOREIGN KEY (entity_id) REFERENCES entity_base(entity_id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--
------------------------------------------------------
-- Added on 05-11-2-15 by Prasad BHVN & Santosh
-----------------------------------------------------

 
--
-- Name: attribute_value_file_storage_id; Type: FK CONSTRAINT; Schema: client_template; Owner: -
--

ALTER TABLE attribute_value_storage_base
    ADD CONSTRAINT attribute_value_file_storage_id_fkey FOREIGN KEY (attribute_value_file_storage_id) 
    	REFERENCES attribute_value_file_storage_base(attribute_value_file_storage_id) ON UPDATE CASCADE ON DELETE CASCADE;
 

-- Function: testclient.check_attribute_file_storage_id()

CREATE FUNCTION check_attribute_file_storage_id() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    att_vals client_template.attribute_base;
BEGIN
    -- check whether attribute_file_storage_id is Null or Not
    IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE') THEN
        SELECT * INTO att_vals FROM ONLY client_template.attribute_base WHERE attribute_id = NEW.attribute_id ;
        
		IF (att_vals.attribute_fieldtype_id = 14 OR att_vals.attribute_fieldtype_id = 15) THEN
			IF NEW.attribute_value_file_storage_id IS NOT NULL THEN
				RETURN NEW;
			ELSE
				RAISE EXCEPTION 'Attribute File Storage Id Should not be NULL';
			END IF;
		END IF;
	   	
    END IF;
    RETURN NEW;                 
END;$$;

--
-- Name: chk_att_file_storage_id; Type: TRIGGER; Schema: client_template; Owner: -
--

CREATE TRIGGER check_attribute_file_storage_id BEFORE INSERT OR UPDATE ON attribute_value_storage_base FOR EACH ROW EXECUTE PROCEDURE check_attribute_file_storage_id();
    	


