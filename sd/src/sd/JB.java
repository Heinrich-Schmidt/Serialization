package sd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.math.BigDecimal;
import java.time.Instant;

public class JB implements Serializable {

	private static final long serialVersionUID = -8705777984960668181L;
	private String name;//
	private boolean male;//
	private short age;//
	private long favoriteNumber;//
	private byte countOfChromosomes;//
	private char group;//
	private int countOfHair;//
	private float height;//
	private double weight;//
	private Instant instatntOfHappiness;//
	private BigDecimal countOfAtoms;//
	private JB1 father = new JB1();//
	private Map<Object, Object> map;
	private List<Object> list;
	private Set<Object> set;
	
	public JB() {
	    this.name = "Ivan";
	    this.age = 21;
	}
	public JB(String name, short age) {
	    this.name = name;
	    this.age = age;
	}
	 
	public String getName() {
	    return name;
	}
	 
	public void setMale(boolean male) {
	    this.male = male;
	}
	public boolean isMale() {
	    return male;
	}
	 
	public void setName(String name) {
	    this.name = name;
	}
	    
	public short getAge() {
	    return age;
	}
	 
	public void setAge(short age) {
	    this.age = age;
	}
	public double getWeight() {
	    return weight;
	}
	 
	public void setWeight(double weight) {
	    this.weight = weight;
	}
	public JB1 getFather() {
	    return father;
	}
	 
	public void setFather(JB1 father) {
	    this.father = father;
	}
	public long getFavoriteNumber() {
	    return favoriteNumber;
	}
	 
	public void setFavoriteNumber(long favoriteNumber) {
	    this.favoriteNumber = favoriteNumber;
	}
	public byte getCountOfChromosomes() {
	    return countOfChromosomes;
	}
	 
	public void setCountOfChromosomes(byte countOfChromosomes) {
	    this.countOfChromosomes = countOfChromosomes;
	}
	public char getGroup() {
	    return group;
	}
	 
	public void setGroup(char group) {
	    this.group = group;
	}
	public int getCountOfHair() {
	    return countOfHair;
	}
	 
	public void setCountOfHair(int countOfHair) {
	    this.countOfHair = countOfHair;
	}
	public float getHeight() {
	    return height;
	}
	public void setHeight(float height) {
	    this.height = height;
	}
	public void setInstatntOfHappiness(Instant instatntOfHappiness) {
	    this.instatntOfHappiness = instatntOfHappiness;
	}
	public Instant getInstatntOfHappiness() {
	    return instatntOfHappiness;
	}
	public void setCountOfAtoms(BigDecimal countOfAtoms) {
	    this.countOfAtoms = countOfAtoms;
	}
	public BigDecimal getCountOfAtoms() {
	    return countOfAtoms;
	}
	public void setList(List<Object> list) {
	    this.list = list;
	}
	public List<Object> getList() {
	    return list;
	}
	public void setSet(Set<Object> set) {
	    this.set = set;
	}
	public Set<Object> getSet() {
	    return set;
	}
	public void setMap(Map<Object,Object> map) {
	    this.map = map;
	}
	public Map<Object,Object> getMap() {
	    return map;
	}
	
	 

}
