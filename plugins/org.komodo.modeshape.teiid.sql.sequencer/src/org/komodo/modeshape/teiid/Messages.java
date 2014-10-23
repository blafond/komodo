/*************************************************************************************
 * JBoss, Home of Professional Open Source.
* See the COPYRIGHT.txt file distributed with this work for information
* regarding copyright ownership. Some portions may be licensed
* to Red Hat, Inc. under one or more contributor license agreements.
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
* 02110-1301 USA.
 ************************************************************************************/
package org.komodo.modeshape.teiid;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 */
public class Messages {

    private static final String BUNDLE_NAME = "org.komodo.modeshape.teiid.messages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private static final String DOT = "."; //$NON-NLS-1$

    private static final String UNDERSCORE = "_"; //$NON-NLS-1$

    @SuppressWarnings( "javadoc" )
    public enum ArgCheck {
        isNonNegativeInt,
        isNonPositiveInt,
        isNegativeInt,
        isPositiveInt,
        isStringNonZeroLength,
        isNonNull,
        isNull,
        isInstanceOf,
        isCollectionNotEmpty,
        isMapNotEmpty,
        isArrayNotEmpty,
        isNotSame,
        contains,
        containsKey;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum MMClob {
        MMBlob_0,
        MMBlob_1,
        MMBlob_2,
        MMBlob_3;

        @Override
        public String toString() {
            // Cannot use dots in enums
            return getEnumName(this) + DOT + name().replaceAll(UNDERSCORE, DOT);
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum TeiidSqlSequencer {
        ErrorSequencingContent,
        ErrorParsingContent;
    }

    @SuppressWarnings( "javadoc" )
    public enum TeiidParser {
        Unknown_join_type,
        Aggregate_only_top_level,
        window_only_top_level,
        Unknown_agg_func,
        Invalid_func,
        Integer_parse,
        Float_parse,
        decimal_parse,
        Invalid_id,
        Invalid_alias,
        Invalid_short_name,
        invalid_window,
        function_def,
        view_def,
        pk_exists,
        no_column,
        function_return,
        function_in,
        alter_table_doesnot_exist,
        alter_procedure_doesnot_exist,
        alter_procedure_param_doesnot_exist,
        alter_function_param_doesnot_exist,
        alter_table_param,
        char_val,
        non_position_constant,
        expected_non_reserved,
        lexicalError,
        noParserForVersion,
        invalidNodeType,
        udt_format_wrong,
        proc_type_conflict,
        param_out,
        teiid_version_failure,
        teiid_version_atleast_failure,
        invalid_reserved_word_use,
        no_from_clause,
        invalid_command,
        invalid_function_args,
        invalid_join_clause,
        unexpected_token,
        parsing_error;
        
        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum InvalidPropertyException {
        message;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum Mapping {
        unknown_node_type,
        invalid_criteria_node,
        noCriteria,
        invalidName;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum Misc {
        ReflectionHelper_errorConstructing,
        TeiidVersionNotSupported,
        TeiidVersionFailure,
        Evaluator_noValue,
        ExceptionHolder_convertedException;

        @Override
        public String toString() {
            return this.name().replaceAll(UNDERSCORE, DOT);
        }
    }
    
    @SuppressWarnings( "javadoc" )
    public enum SystemSource {
        array_length_description,
        array_param1,
        array_length_result,
        array_get_description,
        array_get_param2,
        array_get_result,
        Add_description,
        unescape_param1,
        unescape_result,
        unescape_description,
        Add_result_description,
        Subtract_description,
        Subtract_result_description,
        Multiply_description,
        Multiply_result_description,
        Divide_description,
        Divide_result_description,
        Ceiling_description,
        Exp_description,
        Floor_description,
        Log_description,
        Log10_description,
        Acos_description,
        Asin_description,
        Atan_description,
        Atan2_description,
        Cos_description,
        Cot_description,
        Degrees_description,
        Pi_description,
        Radians_description,
        Sin_description,
        Tan_description,
        Bitand_description,
        Bitand_result_description,
        Bitor_description,
        Bitor_result_description,
        Bitxor_description,
        Bitxor_result_description,
        Bitnot_description,
        Bitnot_result_description,
        Curdate_description,
        Curtime_description,
        Now_description,
        Dayname_result_d_description,
        Dayname_result_ts_description,
        Dayofmonth_result_d_description,
        Dayofmonth_result_ts_description,
        Dayofweek_result_d_description,
        Dayofweek_result_ts_description,
        Dayofyear_result_d_description,
        Dayofyear_result_ts_description,
        Month_result_d_description,
        Month_result_ts_description,
        Monthname_result_d_description,
        Monthname_result_ts_description,
        Week_result_d_description,
        Week_result_ts_description,
        Year_result_d_description,
        Year_result_ts_description,
        Hour_result_t_description,
        Hour_result_ts_description,
        Minute_result_t_description,
        Minute_result_ts_description,
        Second_result_t_description,
        Second_result_ts_description,
        Quarter_result_d_description,
        Quarter_result_ts_description,
        Length_result,
        Ucase_result,
        Lcase_result,
        Lower_result,
        Upper_result,
        UcaseClob_result,
        LcaseClob_result,
        LowerClob_result,
        UpperClob_result,
        Left_result,
        Right_result,
        Formattime_description,
        Formattime_result_description,
        Formatdate_description,
        Formatdate_result_description,
        Formattimestamp_description,
        Formattimestamp_result_description,
        Parsetime_description,
        Parsetime_result_description,
        Parsedate_description,
        Parsedate_result_description,
        Parsetimestamp_description,
        Parsetimestamp_result_description,
        Formatinteger_description,
        Formatinteger_result_description,
        Formatlong_description,
        Formatlong_result_description,
        Formatdouble_description,
        Formatdouble_result_description,
        Formatfloat_description,
        Formatfloat_result_description,
        Formatbiginteger_description,
        Formatbiginteger_result_description,
        Formatbigdecimal_description,
        Formatbigdecimal_result_description,
        Parseinteger_description,
        Parseinteger_result_description,
        Parselong_description,
        Parselong_result_description,
        Parsedouble_description,
        Parsedouble_result_description,
        Parsefloat_description,
        Parsefloat_result_description,
        Parsebiginteger_description,
        Parsebiginteger_result_description,
        Parsebigdecimal_description,
        Parsebigdecimal_result_description,
        Arith_left_op,
        Arith_right_op,
        Abs_description,
        Abs_arg,
        Abs_result_description,
        Rand_description,
        Rand_arg,
        Rand_result_description,
        uuid_description,
        uuid_result_description,
        trim_description,
        trim_arg1,
        trim_arg2,
        trim_arg3,
        trim_result,
        Double_arg2,
        Atan_arg1,
        Atan_arg2,
        Mod_description,
        Mod_result_description,
        Power_description,
        Power_arg1,
        Power_arg2,
        Power_result_description,
        Round_description,
        Round_arg1,
        Round_arg2,
        Round_result_description,
        Sign_description,
        Sign_arg1,
        Sign_result_description,
        Sqrt_description,
        Sqrt_arg1,
        Sqrt_result_description,
        Timestampadd_d_description,
        Timestampadd_d_arg1,
        Timestampadd_d_arg2,
        Timestampadd_d_arg3,
        Timestampadd_d_result_description,
        Timestampadd_t_description,
        Timestampadd_t_arg1,
        Timestampadd_t_arg2,
        Timestampadd_t_arg3,
        Timestampadd_t_result_description,
        Timestampadd_ts_description,
        Timestampadd_ts_arg1,
        Timestampadd_ts_arg2,
        Timestampadd_ts_arg3,
        Timestampadd_ts_result,
        Timestampdiff_ts_description,
        Timestampdiff_ts_arg1,
        Timestampdiff_ts_arg2,
        Timestampdiff_ts_arg3,
        Timestampdiff_ts_result_description,
        TimestampCreate_description,
        TimestampCreate_arg1,
        TimestampCreate_arg2,
        TimestampCreate_result_description,
        Stringfunc_arg1,
        Clobfunc_arg1,
        Concat_description,
        Concat_arg1,
        Concat_arg2,
        Concat_result_description,
        Concatop_description,
        Concatop_arg1,
        Concatop_arg2,
        Concatop_result_description,
        Substring_description,
        Substring_arg1,
        Substring_arg2,
        Substring_arg3,
        Substring_result,
        Susbstring2_description,
        Substring2_arg1,
        Substring2_arg2,
        Substring2_result,
        Left_description,
        Left_arg1,
        Left_arg2,
        Left2_result,
        Right_description,
        Right_arg1,
        Right_arg2,
        Right2_result,
        Locate_description,
        Locate_arg1,
        Locate_arg2,
        Locate_arg3,
        Locate_result,
        Locate2_description,
        Locate2_arg1,
        Locate2_arg2,
        Locate2_result,
        Replace_description,
        Replace_arg1,
        Replace_arg2,
        Replace_arg3,
        Replace_result,
        Repeat_description,
        Repeat_arg1,
        Repeat_arg2,
        Repeat_result,
        Space_description,
        Space_arg1,
        Space_result,
        Insert_description,
        Insert_arg1,
        Insert_arg2,
        Insert_arg3,
        Insert_arg4,
        Insert_result,
        Ascii_description,
        Ascii_arg1,
        Ascii_result,
        Ascii2_description,
        Ascii2_arg1,
        Ascii2_result,
        Chr_description,
        Chr_arg1,
        Chr_result,
        Char_description,
        Char_arg1,
        Char_result,
        Initcap_description,
        Initcap_arg1,
        Initcap_result,
        Lpad_description,
        Lpad_arg1,
        Lpad_arg2,
        Lpad_result,
        Lpad3_description,
        Lpad3_arg1,
        Lpad3_arg2,
        Lpad3_arg3,
        Lpad3_result,
        Rpad1_description,
        Rpad1_arg1,
        Rpad1_arg2,
        Rpad1_result,
        Rpad3_description,
        Rpad3_arg1,
        Rpad3_arg2,
        Rpad3_arg3,
        Rpad3_result,
        Translate_description,
        Translate_arg1,
        Translate_arg2,
        Translate_arg3,
        Translate_result,
        Convert_arg1,
        Convert_arg2,
        Convert_result,
        Context_description,
        Context_arg1,
        Context_arg2,
        Context_result,
        Rowlimit_description,
        RowlimitException_description,
        Rowlimit_arg1,
        Rowlimit_result,
        Decode1_description,
        Decode1_arg1,
        Decode1_arg2,
        Decode1_result,
        Decode2_description,
        Decode2_arg1,
        Decode2_arg2,
        Decode2_arg3,
        Decode2_result,
        Lookup_description,
        Lookup_arg1,
        Lookup_arg2,
        Lookup_arg3,
        Lookup_arg4,
        Lookup_result,
        User_description,
        User_result,
        current_database_description,
        current_database_result,
        Env_description,
        Env_varname,
        Env_result,
        Nvl_description,
        Nvl_arg1,
        Nvl_arg2,
        Nvl_result,
        Ifnull_description,
        Ifnull_arg1,
        Ifnull_arg2,
        Ifnull_result,
        Formattime_arg1,
        Formattime_arg2,
        Formatdate_arg1,
        Formatdate_arg2,
        Formattimestamp_arg1,
        Formattimestamp_arg2,
        Parsetime_arg1,
        Parsetime_arg2,
        Parsedate_arg1,
        Parsedate_arg2,
        Parsetimestamp_arg1,
        Parsetimestamp_arg2,
        Formatnumber_arg1,
        Formatnumber_arg2,
        Parsenumber_arg1,
        Parsenumber_arg2,
        Bitfunc_arg1,
        Bitfunc2_arg1,
        Bitfunc2_arg2,
        Convert_description,
        xpathvalue_description,
        xpath_param1,
        xpath_param2,
        xpathvalue_result,
        xsltransform_description,
        xsltransform_param1,
        xsltransform_param2,
        xsltransform_result,
        xmlconcat_description,
        xmlconcat_param1,
        xmlconcat_param2,
        xmlcomment_description,
        xmlcomment_param2,
        xmlcomment_result,
        xmlconcat_result,
        xmlpi_description,
        xmlpi_param1,
        xmlpi_param2,
        xmlpi_result,
        jsonToXml_description,
        jsonToXml_param1,
        jsonToXml_param2,
        jsonToXml_result,
        modifyTimeZone_description,
        modifyTimeZone_param1,
        modifyTimeZone_param2,
        modifyTimeZone_param3,
        modifyTimeZone_result,
        CommandPayload_desc0,
        CommandPayload_desc1,
        CommandPayload_result,
        CommandPayload_property,
        hasRole_description,
        hasRole_param1,
        hasRole_param2,
        hasRole_result,
        from_unixtime_description,
        from_unixtime_param1,
        from_unixtime_result,
        nullif_description,
        nullif_param1,
        nullif_result,
        coalesce_description,
        coalesce_param1,
        coalesce_result,
        to_chars_description,
        to_chars_param1,
        to_chars_param2,
        to_chars_param3,
        to_chars_result,
        to_bytes_description,
        to_bytes_param1,
        to_bytes_param2,
        to_bytes_param3,
        to_bytes_result,
        session_id_description,
        session_id_result,
        endswith_description,
        endswith_arg1,
        endswith_arg2,
        endswith_result,
        jsonParse_description,
        jsonParse_param1,
        jsonParse_param2,
        jsonParse_result,
        jsonArray_description,
        jsonArray_param1,
        jsonArray_result,
        teiid_session_get_description,
        teiid_session_get_param1,
        teiid_session_get_result,
        teiid_session_set_description,
        teiid_session_set_param1,
        teiid_session_set_param2,
        teiid_session_set_result,
        encode_description,
        encode_arg1,
        encode_arg2,
        encode_result,
        decode_description,
        decode_arg1,
        decode_arg2,
        decode_result,
        mvstatus_param1,
        mvstatus_param2,
        mvstatus_param3,
        mvstatus_param4, 
        mvstatus_param5,
        mvstatus_result,
        mvstatus_description,
        tokenize_param1,
        tokenize_param2,
        tokenize_result,
        tokenize_description;

        @Override
        public String toString() {
            return getEnumName(this) + DOT + name();
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum ERR {
        ERR_100_001_0001,
        ERR_003_029_0002,
        ERR_003_029_0003,
        ERR_015_001_0005,
        ERR_015_001_0044,
        ERR_015_001_0052,
        ERR_015_001_0057,
        ERR_015_001_0066,
        ERR_015_001_0069,
        ERR_015_002_0009,
        ERR_015_002_0010,
        ERR_015_002_0011,
        ERR_015_004_0010,
        ERR_015_004_0036,
        ERR_015_006_0001,
        ERR_015_006_0034,
        ERR_015_006_0042,
        ERR_015_006_0048,
        ERR_015_006_0049,
        ERR_015_006_0051,
        ERR_015_006_0054,
        ERR_015_008_0007,
        ERR_015_008_0022,
        ERR_015_008_0025,
        ERR_015_008_0032,
        ERR_015_008_0046,
        ERR_015_008_0047,
        ERR_015_008_0049,
        ERR_015_008_0055,
        ERR_015_008_0056,
        ERR_015_009_0002,
        ERR_015_010_0001,
        ERR_015_010_0002,
        ERR_015_010_0003,
        ERR_015_010_0006,
        ERR_015_010_0009,
        ERR_015_010_0010,
        ERR_015_010_0011,
        ERR_015_010_0014,
        ERR_015_010_0015,
        ERR_015_010_0016,
        ERR_015_010_0017,
        ERR_015_010_0018,
        ERR_015_010_0021,
        ERR_015_010_0022,
        ERR_015_010_0023,
        ERR_015_010_0029,
        ERR_015_010_0031,
        ERR_015_010_0032,
        ERR_015_010_0035,
        ERR_015_010_0036,
        ERR_015_010_0037,
        ERR_015_010_0038,
        ERR_015_010_0039,
        ERR_015_012_0001,
        ERR_015_012_0002,
        ERR_015_012_0003,
        ERR_015_012_0004,
        ERR_015_012_0005,
        ERR_015_012_0006,
        ERR_015_012_0007,
        ERR_015_012_0008,
        ERR_015_012_0009,
        ERR_015_012_0010,
        ERR_015_012_0011,
        ERR_015_012_0012,
        ERR_015_012_0013,
        ERR_015_012_0014,
        ERR_015_012_0015,
        ERR_015_012_0016,
        ERR_015_012_0017,
        ERR_015_012_0018,
        ERR_015_012_0019,
        ERR_015_012_0021,
        ERR_015_012_0022,
        ERR_015_012_0023,
        ERR_015_012_0024,
        ERR_015_012_0025,
        ERR_015_012_0026,
        ERR_015_012_0027,
        ERR_015_012_0029,
        ERR_015_012_0030,
        ERR_015_012_0031,
        ERR_015_012_0032,
        ERR_015_012_0033,
        ERR_015_012_0034,
        ERR_015_012_0037,
        ERR_015_012_0039,
        ERR_015_012_0041,
        ERR_015_012_0042,
        ERR_015_012_0052,
        ERR_015_012_0053,
        ERR_015_012_0055,
        ERR_015_012_0059,
        ERR_015_012_0060,
        ERR_015_012_0062,
        ERR_015_012_0063,
        ERR_015_012_0064,
        ERR_015_012_0065,
        ERR_015_012_0067,
        ERR_015_012_0069,
        ERR_018_005_0095;
        
        @Override
        public String toString() {
            // Cannot use dots in enums
            return this.name().replaceAll(UNDERSCORE, DOT);
        }
    }

    @SuppressWarnings( "javadoc" )
    public enum TEIID {
        TEIID10006,
        TEIID10007,
        TEIID10008,
        TEIID10009,
        TEIID10010,
        TEIID10011,
        TEIID10013,
        TEIID10014,
        TEIID10015,
        TEIID10016,
        TEIID10017,
        TEIID10018,
        TEIID10030,
        TEIID10032,
        TEIID10052,
        TEIID10058,
        TEIID10059,
        TEIID10060,
        TEIID10061,
        TEIID10063,
        TEIID10068,
        TEIID10070,
        TEIID10071,
        TEIID10072,
        TEIID10073,
        TEIID10074,
        TEIID10076,
        TEIID10077,
        TEIID10078,
        TEIID10080,
        TEIID10081,
        TEIID10083,
        TEIID20000,
        TEIID20001,
        TEIID20005,
        TEIID20007,
        TEIID20009,
        TEIID20016,
        TEIID20018,
        TEIID20019,
        TEIID20020,
        TEIID20021,
        TEIID20023,
        TEIID20028,
        TEIID20029,
        TEIID20030,
        TEIID20031,
        TEIID20032,
        TEIID20034,
        TEIID30001,
        TEIID30002,
        TEIID30003,
        TEIID30004,
        TEIID30005,
        TEIID30006,
        TEIID30008,
        TEIID30009,
        TEIID30011,
        TEIID30012,
        TEIID30013,
        TEIID30014,
        TEIID30015,
        TEIID30016,
        TEIID30017,
        TEIID30018,
        TEIID30019,
        TEIID30020,
        TEIID30021,
        TEIID30022,
        TEIID30023,
        TEIID30024,
        TEIID30025,
        TEIID30026,
        TEIID30027,
        TEIID30028,
        TEIID30029,
        TEIID30030,
        TEIID30031,
        TEIID30032,
        TEIID30033,
        TEIID30034,
        TEIID30035,
        TEIID30040,
        TEIID30041,
        TEIID30042,
        TEIID30045,
        TEIID30048,
        TEIID30059,
        TEIID30060,
        TEIID30061,
        TEIID30063,
        TEIID30065,
        TEIID30066,
        TEIID30067,
        TEIID30068,
        TEIID30069,
        TEIID30070,
        TEIID30071,
        TEIID30072,
        TEIID30074,
        TEIID30075,
        TEIID30077,
        TEIID30079,
        TEIID30082,
        TEIID30083,
        TEIID30084,
        TEIID30085,
        TEIID30086,
        TEIID30087,
        TEIID30088,
        TEIID30089,
        TEIID30090,
        TEIID30091,
        TEIID30093,
        TEIID30094,
        TEIID30095,
        TEIID30096,
        TEIID30097,
        TEIID30098,
        TEIID30099,
        TEIID30100,
        TEIID30101,
        TEIID30102,
        TEIID30112,
        TEIID30114,
        TEIID30116,
        TEIID30117,
        TEIID30118,
        TEIID30121,
        TEIID30123,
        TEIID30124,
        TEIID30125,
        TEIID30126,
        TEIID30127,
        TEIID30128,
        TEIID30129,
        TEIID30130,
        TEIID30131,
        TEIID30133,
        TEIID30134,
        TEIID30135,
        TEIID30136,
        TEIID30137,
        TEIID30138,
        TEIID30139,
        TEIID30140,
        TEIID30141,
        TEIID30143,
        TEIID30144,
        TEIID30145,
        TEIID30146,
        TEIID30147,
        TEIID30151,
        TEIID30152,
        TEIID30153,
        TEIID30154,
        TEIID30155,
        TEIID30156,
        TEIID30158,
        TEIID30160,
        TEIID30161,
        TEIID30164,
        TEIID30166,
        TEIID30168,
        TEIID30170,
        TEIID30171,
        TEIID30172,
        TEIID30174,
        TEIID30175,
        TEIID30176,
        TEIID30177,
        TEIID30178,
        TEIID30179,
        TEIID30181,
        TEIID30182,
        TEIID30183,
        TEIID30184,
        TEIID30190,
        TEIID30192,
        TEIID30193,
        TEIID30211,
        TEIID30212,
        TEIID30213,
        TEIID30216,
        TEIID30226,
        TEIID30227,
        TEIID30229,
        TEIID30230,
        TEIID30231,
        TEIID30232,
        TEIID30233,
        TEIID30236,
        TEIID30238,
        TEIID30239,
        TEIID30240,
        TEIID30241,
        TEIID30244,
        TEIID30250,
        TEIID30251,
        TEIID30253,
        TEIID30254,
        TEIID30258,
        TEIID30259,
        TEIID30263,
        TEIID30267,
        TEIID30268,
        TEIID30269,
        TEIID30270,
        TEIID30272,
        TEIID30275,
        TEIID30278,
        TEIID30281,
        TEIID30283,
        TEIID30287,
        TEIID30288,
        TEIID30295,
        TEIID30296,
        TEIID30297,
        TEIID30300,
        TEIID30301,
        TEIID30302,
        TEIID30303,
        TEIID30306,
        TEIID30307,
        TEIID30308,
        TEIID30309,
        TEIID30311,
        TEIID30312,
        TEIID30314,
        TEIID30323,
        TEIID30326,
        TEIID30328,
        TEIID30329,
        TEIID30333,
        TEIID30336,
        TEIID30341,
        TEIID30342,
        TEIID30345,
        TEIID30347,
        TEIID30350,
        TEIID30351,
        TEIID30358,
        TEIID30359,
        TEIID30363,
        TEIID30364,
        TEIID30372,
        TEIID30373,
        TEIID30375,
        TEIID30376,
        TEIID30377,
        TEIID30378,
        TEIID30382,
        TEIID30384,
        TEIID30385,
        TEIID30387,
        TEIID30388,
        TEIID30389,
        TEIID30390,
        TEIID30391,
        TEIID30392,
        TEIID30396,
        TEIID30398,
        TEIID30399,
        TEIID30400,
        TEIID30401,
        TEIID30402,
        TEIID30403,
        TEIID30404,
        TEIID30405,
        TEIID30406,
        TEIID30407,
        TEIID30409,
        TEIID30410,
        TEIID30411,
        TEIID30412,
        TEIID30413,
        TEIID30416,
        TEIID30424,
        TEIID30425,
        TEIID30427,
        TEIID30428,
        TEIID30429,
        TEIID30430,
        TEIID30431,
        TEIID30432,
        TEIID30434,
        TEIID30448,
        TEIID30449,
        TEIID30452,
        TEIID30457,
        TEIID30476,
        TEIID30477,
        TEIID30479,
        TEIID30481,
        TEIID30482,
        TEIID30489,
        TEIID30491,
        TEIID30495,
        TEIID30497,
        TEIID30498,
        TEIID30499,
        TEIID30505,
        TEIID30517,
        TEIID30518,
        TEIID30519,
        TEIID30520,
        TEIID30521,
        TEIID30522,
        TEIID30524,
        TEIID30525,
        TEIID30546,
        TEIID30548,
        TEIID30549,
        TEIID30554,
        TEIID30555,
        TEIID30561,
        TEIID30562,
        TEIID30563,
        TEIID30564,
        TEIID30565,
        TEIID30581,
        TEIID30590,
        TEIID30591,
        TEIID30600,
        TEIID30601,
        TEIID30602,
        TEIID31069,
        TEIID31070,
        TEIID31071,
        TEIID31072,
        TEIID31073,
        TEIID31075,
        TEIID31077,
        TEIID31078,
        TEIID31079,
        TEIID31080,
        TEIID31081,
        TEIID31082,
        TEIID31083,
        TEIID31084,
        TEIID31085,
        TEIID31086,
        TEIID31087,
        TEIID31088,
        TEIID31089,
        TEIID31090,
        TEIID31091,
        TEIID31092,
        TEIID31093,
        TEIID31094,
        TEIID31095,
        TEIID31096,
        TEIID31097,
        TEIID31099,
        TEIID31100,
        TEIID31101,
        TEIID31102,
        TEIID31103,
        TEIID31104,
        TEIID31105,
        TEIID31106,
        TEIID31107,
        TEIID31109,
        TEIID31110,
        TEIID31111,
        TEIID31112,
        TEIID31113,
        TEIID31114,
        TEIID31115,
        TEIID31116,
        TEIID31117,
        TEIID31118,
        TEIID31119,
        TEIID31120,
        TEIID31121,
        TEIID31122,
        TEIID31123,
        TEIID31124,
        TEIID31125,
        TEIID31126,
        TEIID31127,
        TEIID31128,
        TEIID31129,
        TEIID31130,
        TEIID31131,
        TEIID31132,
        TEIID31133,
        TEIID31134,
        TEIID31135,
        TEIID31136,
        TEIID31137,
        TEIID31138,
        TEIID31139,
        TEIID31140,
        TEIID31141,
        TEIID31142,
        TEIID31143,
        TEIID31144,
        TEIID31145,
        TEIID31146,
        TEIID31147,
        TEIID31149,
        TEIID31150,
        TEIID60001,
        TEIID60004,
        TEIID60008,
        TEIID60009,
        TEIID60010,
        TEIID60011,
        TEIID60012,
        TEIID60013,
        TEIID60014,
        TEIID60015,
        TEIID60016,
        TEIID60017,
        TEIID60018,
        TEIID70000,
        TEIID70003,
        TEIID70004,
        TEIID70005,
        TEIID70008,
        TEIID70016,
        TEIID70051,
        TEIID70053,
        TEIID70054,
        TEIID70055;

        @Override
        public String toString() {
            return name();
        }
    }

    private static String getEnumName(Enum<?> enumValue) {
        String className = enumValue.getClass().getName();
        String[] components = className.split("\\$"); //$NON-NLS-1$
        return components[components.length - 1];
    }
    
    private Messages() {
    }

    /**
     * Get the message string for the given {@link TEIID}
     * key. This will output with the TEIID error number
     * prepended to the message in the same way as the
     * teiid client.
     *
     * @param key TEIID enum value
     * @param parameters arguemnts to message
     *
     * @return error message associated with key
     */
    public static String gs(TEIID key, final Object... parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append(" "); //$NON-NLS-1$
        sb.append(getString(key, parameters));
        return sb.toString();
    }

    /**
     * Get message string
     *
     * @param key
     *
     * @return i18n string
     */
    private static String getString(Enum<?> key) {
        try {
            return RESOURCE_BUNDLE.getString(key.toString());
        } catch (final Exception err) {
            String msg;

            if (err instanceof NullPointerException) {
                msg = "<No message available>"; //$NON-NLS-1$
            } else if (err instanceof MissingResourceException) {
                msg = "<Missing message for key \"" + key + "\" in: " + BUNDLE_NAME + '>'; //$NON-NLS-1$ //$NON-NLS-2$
            } else {
                msg = err.getLocalizedMessage();
            }

            return msg;
        }
    }

    /**
     * Get message string with parameters
     *
     * @param key enum value
     * @param parameters arguemnts to message
     *
     * @return i18n string
     */
    public static String getString(Enum<?> key, Object... parameters) {
        String text = getString(key);

        // Check the trivial cases ...
        if (text == null) {
            return '<' + key.toString() + '>';
        }
        if (parameters == null || parameters.length == 0) {
            return text;
        }

        return MessageFormat.format(text, parameters);
    }
}
