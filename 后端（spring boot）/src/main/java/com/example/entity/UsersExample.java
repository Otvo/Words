package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UsersExample() {
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

        public Criteria andUseridIsNull() {
            addCriterion("userID is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userID is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(String value) {
            addCriterion("userID =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(String value) {
            addCriterion("userID <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(String value) {
            addCriterion("userID >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(String value) {
            addCriterion("userID >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(String value) {
            addCriterion("userID <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(String value) {
            addCriterion("userID <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLike(String value) {
            addCriterion("userID like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotLike(String value) {
            addCriterion("userID not like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<String> values) {
            addCriterion("userID in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<String> values) {
            addCriterion("userID not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(String value1, String value2) {
            addCriterion("userID between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(String value1, String value2) {
            addCriterion("userID not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andLearnnumIsNull() {
            addCriterion("learnNum is null");
            return (Criteria) this;
        }

        public Criteria andLearnnumIsNotNull() {
            addCriterion("learnNum is not null");
            return (Criteria) this;
        }

        public Criteria andLearnnumEqualTo(Integer value) {
            addCriterion("learnNum =", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumNotEqualTo(Integer value) {
            addCriterion("learnNum <>", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumGreaterThan(Integer value) {
            addCriterion("learnNum >", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("learnNum >=", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumLessThan(Integer value) {
            addCriterion("learnNum <", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumLessThanOrEqualTo(Integer value) {
            addCriterion("learnNum <=", value, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumIn(List<Integer> values) {
            addCriterion("learnNum in", values, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumNotIn(List<Integer> values) {
            addCriterion("learnNum not in", values, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumBetween(Integer value1, Integer value2) {
            addCriterion("learnNum between", value1, value2, "learnnum");
            return (Criteria) this;
        }

        public Criteria andLearnnumNotBetween(Integer value1, Integer value2) {
            addCriterion("learnNum not between", value1, value2, "learnnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumIsNull() {
            addCriterion("reviewNum is null");
            return (Criteria) this;
        }

        public Criteria andReviewnumIsNotNull() {
            addCriterion("reviewNum is not null");
            return (Criteria) this;
        }

        public Criteria andReviewnumEqualTo(Integer value) {
            addCriterion("reviewNum =", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumNotEqualTo(Integer value) {
            addCriterion("reviewNum <>", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumGreaterThan(Integer value) {
            addCriterion("reviewNum >", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reviewNum >=", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumLessThan(Integer value) {
            addCriterion("reviewNum <", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumLessThanOrEqualTo(Integer value) {
            addCriterion("reviewNum <=", value, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumIn(List<Integer> values) {
            addCriterion("reviewNum in", values, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumNotIn(List<Integer> values) {
            addCriterion("reviewNum not in", values, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumBetween(Integer value1, Integer value2) {
            addCriterion("reviewNum between", value1, value2, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andReviewnumNotBetween(Integer value1, Integer value2) {
            addCriterion("reviewNum not between", value1, value2, "reviewnum");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andPronunciationIsNull() {
            addCriterion("pronunciation is null");
            return (Criteria) this;
        }

        public Criteria andPronunciationIsNotNull() {
            addCriterion("pronunciation is not null");
            return (Criteria) this;
        }

        public Criteria andPronunciationEqualTo(String value) {
            addCriterion("pronunciation =", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationNotEqualTo(String value) {
            addCriterion("pronunciation <>", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationGreaterThan(String value) {
            addCriterion("pronunciation >", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationGreaterThanOrEqualTo(String value) {
            addCriterion("pronunciation >=", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationLessThan(String value) {
            addCriterion("pronunciation <", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationLessThanOrEqualTo(String value) {
            addCriterion("pronunciation <=", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationLike(String value) {
            addCriterion("pronunciation like", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationNotLike(String value) {
            addCriterion("pronunciation not like", value, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationIn(List<String> values) {
            addCriterion("pronunciation in", values, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationNotIn(List<String> values) {
            addCriterion("pronunciation not in", values, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationBetween(String value1, String value2) {
            addCriterion("pronunciation between", value1, value2, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andPronunciationNotBetween(String value1, String value2) {
            addCriterion("pronunciation not between", value1, value2, "pronunciation");
            return (Criteria) this;
        }

        public Criteria andNodeIsNull() {
            addCriterion("node is null");
            return (Criteria) this;
        }

        public Criteria andNodeIsNotNull() {
            addCriterion("node is not null");
            return (Criteria) this;
        }

        public Criteria andNodeEqualTo(Boolean value) {
            addCriterion("node =", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotEqualTo(Boolean value) {
            addCriterion("node <>", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeGreaterThan(Boolean value) {
            addCriterion("node >", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("node >=", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeLessThan(Boolean value) {
            addCriterion("node <", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeLessThanOrEqualTo(Boolean value) {
            addCriterion("node <=", value, "node");
            return (Criteria) this;
        }

        public Criteria andNodeIn(List<Boolean> values) {
            addCriterion("node in", values, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotIn(List<Boolean> values) {
            addCriterion("node not in", values, "node");
            return (Criteria) this;
        }

        public Criteria andNodeBetween(Boolean value1, Boolean value2) {
            addCriterion("node between", value1, value2, "node");
            return (Criteria) this;
        }

        public Criteria andNodeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("node not between", value1, value2, "node");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
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