package watchlistarr.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GraphQLQuery {
    public String query;
    public Object variables;

    public GraphQLQuery(String query) { this.query = query; }
    public GraphQLQuery(String query, Object variables) { this.query = query; this.variables = variables; }
}
