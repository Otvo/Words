package com.example.entity;

import java.util.ArrayList;
import java.util.List;

public class DicExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DicExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andWordIsNull() {
            addCriterion("word is null");
            return (Criteria) this;
        }

        public Criteria andWordIsNotNull() {
            addCriterion("word is not null");
            return (Criteria) this;
        }

        public Criteria andWordEqualTo(String value) {
            addCriterion("word =", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordNotEqualTo(String value) {
            addCriterion("word <>", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordGreaterThan(String value) {
            addCriterion("word >", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordGreaterThanOrEqualTo(String value) {
            addCriterion("word >=", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordLessThan(String value) {
            addCriterion("word <", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordLessThanOrEqualTo(String value) {
            addCriterion("word <=", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordLike(String value) {
            addCriterion("word like", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordNotLike(String value) {
            addCriterion("word not like", value, "word");
            return (Criteria) this;
        }

        public Criteria andWordIn(List<String> values) {
            addCriterion("word in", values, "word");
            return (Criteria) this;
        }

        public Criteria andWordNotIn(List<String> values) {
            addCriterion("word not in", values, "word");
            return (Criteria) this;
        }

        public Criteria andWordBetween(String value1, String value2) {
            addCriterion("word between", value1, value2, "word");
            return (Criteria) this;
        }

        public Criteria andWordNotBetween(String value1, String value2) {
            addCriterion("word not between", value1, value2, "word");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNull() {
            addCriterion("meaning is null");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNotNull() {
            addCriterion("meaning is not null");
            return (Criteria) this;
        }

        public Criteria andMeaningEqualTo(String value) {
            addCriterion("meaning =", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotEqualTo(String value) {
            addCriterion("meaning <>", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThan(String value) {
            addCriterion("meaning >", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThanOrEqualTo(String value) {
            addCriterion("meaning >=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThan(String value) {
            addCriterion("meaning <", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThanOrEqualTo(String value) {
            addCriterion("meaning <=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLike(String value) {
            addCriterion("meaning like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotLike(String value) {
            addCriterion("meaning not like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningIn(List<String> values) {
            addCriterion("meaning in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotIn(List<String> values) {
            addCriterion("meaning not in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningBetween(String value1, String value2) {
            addCriterion("meaning between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotBetween(String value1, String value2) {
            addCriterion("meaning not between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andEnAccentIsNull() {
            addCriterion("en_accent is null");
            return (Criteria) this;
        }

        public Criteria andEnAccentIsNotNull() {
            addCriterion("en_accent is not null");
            return (Criteria) this;
        }

        public Criteria andEnAccentEqualTo(String value) {
            addCriterion("en_accent =", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentNotEqualTo(String value) {
            addCriterion("en_accent <>", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentGreaterThan(String value) {
            addCriterion("en_accent >", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentGreaterThanOrEqualTo(String value) {
            addCriterion("en_accent >=", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentLessThan(String value) {
            addCriterion("en_accent <", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentLessThanOrEqualTo(String value) {
            addCriterion("en_accent <=", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentLike(String value) {
            addCriterion("en_accent like", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentNotLike(String value) {
            addCriterion("en_accent not like", value, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentIn(List<String> values) {
            addCriterion("en_accent in", values, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentNotIn(List<String> values) {
            addCriterion("en_accent not in", values, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentBetween(String value1, String value2) {
            addCriterion("en_accent between", value1, value2, "enAccent");
            return (Criteria) this;
        }

        public Criteria andEnAccentNotBetween(String value1, String value2) {
            addCriterion("en_accent not between", value1, value2, "enAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentIsNull() {
            addCriterion("us_accent is null");
            return (Criteria) this;
        }

        public Criteria andUsAccentIsNotNull() {
            addCriterion("us_accent is not null");
            return (Criteria) this;
        }

        public Criteria andUsAccentEqualTo(String value) {
            addCriterion("us_accent =", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentNotEqualTo(String value) {
            addCriterion("us_accent <>", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentGreaterThan(String value) {
            addCriterion("us_accent >", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentGreaterThanOrEqualTo(String value) {
            addCriterion("us_accent >=", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentLessThan(String value) {
            addCriterion("us_accent <", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentLessThanOrEqualTo(String value) {
            addCriterion("us_accent <=", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentLike(String value) {
            addCriterion("us_accent like", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentNotLike(String value) {
            addCriterion("us_accent not like", value, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentIn(List<String> values) {
            addCriterion("us_accent in", values, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentNotIn(List<String> values) {
            addCriterion("us_accent not in", values, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentBetween(String value1, String value2) {
            addCriterion("us_accent between", value1, value2, "usAccent");
            return (Criteria) this;
        }

        public Criteria andUsAccentNotBetween(String value1, String value2) {
            addCriterion("us_accent not between", value1, value2, "usAccent");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}