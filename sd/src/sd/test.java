package sd;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class test {

	public static void main(String[] args) throws IllegalAccessException, CycleException {
		// TODO Auto-generated method stub
		JB person = new JB("Sergay", (short) 23);
		JB1 father = new JB1("Semen",  (short) 48);
		JB1 mother = new JB1("Anna",  (short) 43);
		person.setCountOfAtoms(new BigDecimal("6712356488896423577841120014"));
		person.setCountOfChromosomes((byte)46);
		person.setCountOfHair(845236);
		person.setFavoriteNumber((long)Math.pow(13, 13));
		person.setGroup('C');
		person.setWeight(78.85);
		person.setHeight((float)181.23);
		person.setMale(true);
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
		String str = "2007-07-07";
		Date parsingDate;
	      try {
	         parsingDate = ft.parse(str); 
	         Instant timestamp = parsingDate.toInstant();
	         person.setInstatntOfHappiness(timestamp);
	      }catch (Exception e) { 
	         
	      }
		person.setFather(father);
		List<Object> parentsList = new ArrayList<Object>();
		Set<Object> parentsSet = new HashSet<Object>();
		Map<Object, Object> parentsMap = new HashMap<Object, Object>();
		parentsList.add(father);
		parentsList.add(mother);
		person.setList(parentsList);
		parentsSet.add(father);
		parentsSet.add(mother);
		//parentsSet.add(person);
		person.setSet(parentsSet);
		parentsMap.put(mother, father);
		person.setMap(parentsMap);
		byte[] o = new serializer().serialize(person);
		int index = o[0]+1;
		for(int i = 0 ; i < o.length; i++) {
			if (i==index) {
				index += o[i]+1;
				System.out.println(i + " ["+o[i]+"]"+" : <length>");
			}else {
			System.out.println(i + " ["+o[i]+"]"+" : " + (char)o[i]);
			}
  	    }	
		System.out.println();
		JB cjb = (JB)new serializer().deserialize(o);
		System.out.println("before: "+ person.getAge());	
		System.out.println("after_: "+ cjb.getAge());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getName());	
		System.out.println("after_: "+ cjb.getName());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getWeight());	
		System.out.println("after_: "+ cjb.getWeight());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getFather().getName());	
		System.out.println("after_: "+ cjb.getFather().getName());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getFather().getAge());	
		System.out.println("after_: "+ cjb.getFather().getAge());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getCountOfAtoms());	
		System.out.println("after_: "+ cjb.getCountOfAtoms());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getCountOfChromosomes());	
		System.out.println("after_: "+ cjb.getCountOfChromosomes());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getCountOfHair());	
		System.out.println("after_: "+ cjb.getCountOfHair());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getFavoriteNumber());	
		System.out.println("after_: "+ cjb.getFavoriteNumber());
		System.out.println("-----------------");
		System.out.println("before: "+ person.getInstatntOfHappiness());	
		System.out.println("after_: "+ cjb.getInstatntOfHappiness());
		System.out.println("-----------------");
		System.out.println("before: "+ ((JB1)person.getList().get(1)).getName());	
		System.out.println("after_: "+ ((JB1)cjb.getList().get(1)).getName());
		System.out.println("-----------------");
		System.out.println("before: "+ ((JB1)person.getSet().iterator().next()).getName());	
		System.out.println("after_: "+ ((JB1)cjb.getSet().iterator().next()).getName());
		System.out.println("-----------------");
		for (Map.Entry<Object, Object> entry : person.getMap().entrySet()) {
	        System.out.println("before: " + ((JB1)entry.getKey()).getName() + ", " + ((JB1)entry.getValue()).getName());           
	    }
		for (Map.Entry<Object, Object> entry : cjb.getMap().entrySet()) {
	        System.out.println("after_: " + ((JB1)entry.getKey()).getName() + ", " + ((JB1)entry.getValue()).getName());           
	    }
		
	}

}
