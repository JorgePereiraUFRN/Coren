package experimento;

import java.io.Serializable;

public class Query<BaseValue> {

	private GenericAtribute genericAtribute;
	private QueryComparassion queryComparassion;
	private BaseValue baseValue;

	public Query(GenericAtribute a, QueryComparassion qc, BaseValue baseValue) {
		super();
		this.genericAtribute = a;
		this.queryComparassion = qc;
		this.baseValue = baseValue;
	}

	public GenericAtribute getGenericAtribute() {
		return genericAtribute;
	}

	public void setGenericAtribute(GenericAtribute genericAtribute) {
		this.genericAtribute = genericAtribute;
	}

	public QueryComparassion getQueryComparassion() {
		return queryComparassion;
	}

	public void setQueryComparassion(QueryComparassion queryComparassion) {
		this.queryComparassion = queryComparassion;
	}

	public BaseValue getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(BaseValue baseValue) {
		this.baseValue = baseValue;
	}

	
}
