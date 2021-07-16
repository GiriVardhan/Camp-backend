package com.jbent.peoplecentral.util.sql;

import java.util.ArrayList;
import java.util.List;

import com.jbent.peoplecentral.exception.SqlParseException;

public class PGHelper {

	/**
	 * Method parses a postgres Row into a List of Strings.
	 * <p>
	 * The postgres row is represented by a String and consists of one or more columns, that are separated by a comma.
	 * The row must begin with an open bracket and must end with a closing bracket.
	 * Each column must begin with a letter or a quote. If a column begins with a quote, the column must end with a quote.
	 * Inside quotation a quote is represented by a double quote or by backslash and quote, a backslash is represented by double backslash.
	 *
	 * @param value
	 * @return List of Strings
	 * @throws SqlParseException
	 */
	public static List<String> postgresROW2StringList(String value) throws SqlParseException
	{
		return postgresROW2StringList(value, 128);
	}

	/**
	 * Method parses a postgres Row into a List of Strings.
	 * <p>
	 * The postgres row is represented by a String and consists of one or more columns, that are separated by a comma.
	 * The row must begin with an open bracket and must end with a closing bracket.
	 * Each column must begin with a letter or a quote. If a column begins with a quote, the column must end with a quote.
	 * Inside quotation a quote is represented by a double quote or by backslash and quote, a backslash is represented by double backslash.
	 * <p>
	 * The appendStringSize is the Size for StringBuilder.
	 *
	 * @param value, the postgres Row
	 * @param appendStringSize
	 * @return List of Strings
	 * @throws SqlParseException
	 */
	public static List<String> postgresROW2StringList(String value, int appendStringSize) throws SqlParseException
	{
		if (!(value.startsWith("(") && value.endsWith(")")))
			throw new SqlParseException("postgresROW2StringList() ROW must begin with '(' and end with ')': " + value);

		List<String> result = new ArrayList<String>();

		char[] c = value.toCharArray();

		StringBuilder element = new StringBuilder(appendStringSize);
		int i = 1;
		while (c[i] != ')')
		{
			if (c[i] == ',')
			{
				if (c[i+1] == ',')
				{
					result.add(new String());
				}else if (c[i+1] == ')')
				{
					result.add(new String());
				}
				i++;
			}else if (c[i] == '\"')
			{
				i++;
				boolean insideQuote = true;
				while(insideQuote)
				{
					char nextChar = c[i + 1];
					if(c[i] == '\"')
					{
						if (nextChar == ',' || nextChar == ')')
						{
							result.add(element.toString());
							element = new StringBuilder(appendStringSize);
							insideQuote = false;
						}else if(nextChar == '\"')
						{
							i++;
							element.append(c[i]);
						}else
						{
							throw new SqlParseException("postgresROW2StringList() char after \" is not valid");
						}
					}else if (c[i] == '\\')
					{
						if(nextChar == '\\' || nextChar == '\"')
						{
							i++;
							element.append(c[i]);
						}else
						{
							throw new SqlParseException("postgresROW2StringList() char after \\ is not valid");
						}
					}else
					{
						element.append(c[i]);
					}
					i++;
				}
			}else
			{
				while(!(c[i] == ',' || c[i] == ')'))
				{
					element.append(c[i]);
					i++;

				}
				result.add(element.toString());
				element = new StringBuilder(appendStringSize); // we aways loose the last object here, but its easier then checking for flag every time before append (definitely we loose some performance here)
			}
		}
		return result;
	}

	public static String escapeObjectForPostgres(Object o){
		if(o == null){ return "";}
		String ret = o.toString();
		ret = ret.replace("\\", "\\\\");
		ret = ret.replace("\"", "\\\"");
		ret = ret.replace(",", "\\,");
		ret = ret.replace(")", "\\)");
		return ret;
	}
	
}