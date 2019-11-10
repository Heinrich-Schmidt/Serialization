package sd;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class serializer {
public static final String ENCODING = "UTF-8";
private final int max = Byte.MAX_VALUE;
private int d_index = 0;
private ArrayList<Object> stack = new ArrayList<>();

public byte[] serialize(Object anyBean) throws CycleException, IllegalAccessException{
   	if (isCircular(anyBean)) {
        throw new CycleException("Circular exception in" + anyBean.getClass());
    }else {
    ArrayList<Byte> arrayBefore = new ArrayList<>();
    String className = anyBean.getClass().getCanonicalName();
	serialize(anyBean, className, arrayBefore);
	
    byte[] result = new byte[arrayBefore.size()];
    for(int i = 0; i < arrayBefore.size(); i++) result[i] = arrayBefore.get(i);
	return  result;
    }
}

public Object deserialize(byte[] data) {
	ArrayList<Byte> unitBytes = readBytes(data);
	Class<?> aClass = null;
	try {
		aClass = Class.forName(returnString(unitBytes));
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return deserialize(data, aClass);
}

private void serialize(Object anyBean, String className, ArrayList<Byte> arrayBefore){
    try {
 
        arrayBefore.add((byte)className.getBytes(ENCODING).length);//кодирование имени класса
        for (byte b : className.getBytes(ENCODING)) {
            arrayBefore.add(b);
        }
        fieldsSerialization(anyBean, arrayBefore);//кодирование полей класса
        arrayBefore.add((byte)0);
    } catch (IllegalAccessException | UnsupportedEncodingException e) {
        e.printStackTrace();
    }
	
}

private ArrayList<Byte> readBytes(byte[] data) { //считывает единицу информации из общего массива байтов
	ArrayList<Byte> output = new ArrayList<>();
	if(data[d_index]==max) {
		int stop = ++d_index+max;
	    for (; d_index < stop; d_index++) {
	    	output.add(data[d_index]);
	    }
	    if(data[d_index+1]!=0)output.addAll(readBytes(data));
	}else {
		int stop = data[d_index] + ++d_index;
		for (; d_index <  + stop; d_index++) {
			output.add(data[d_index]);
	    }
	}
	return output;
}

private String returnString(ArrayList<Byte> output) { //конвертирует список байтов в строку
	char[] charArray = new char[output.size()];
    for (int i = 0; i < output.size(); i++) {
        charArray[i] = (char)(byte)output.get(i);
    }
	return String.valueOf(charArray);
}

private Object deserialize(byte[] data, Class<?> aClass) {
	ArrayList<Byte> unitBytes;
	Object anInstance = new Object();
	try {
    	anInstance =  aClass.getDeclaredConstructor().newInstance();
		while(data[d_index] != 0) {
			unitBytes = readBytes(data);
			Field field = aClass.getDeclaredField(returnString(unitBytes));
			field.setAccessible(true);
		    if ((field.getType() == Integer.class)||(field.getType() == int.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Integer.parseInt((returnString(unitBytes))));
		    	}catch(Exception e) {}
		    }else if (field.getType() == String.class) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, returnString(unitBytes));
		    	}catch(Exception e) {}
		    }else if((field.getType() == Double.class)||(field.getType() == double.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Double.parseDouble((returnString(unitBytes))));
		    	}catch(Exception e) {}
		    }else if((field.getType() == Boolean.class)||(field.getType() == boolean.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Boolean.parseBoolean((returnString(unitBytes))));
		    	}catch(Exception e) {}					
		    }else if((field.getType() == Short.class)||(field.getType() == short.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Short.parseShort((returnString(unitBytes))));
		    	}catch(Exception e) {}	
		    }else if((field.getType() == Long.class)||(field.getType() == long.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Long.parseLong((returnString(unitBytes))));
		    	}catch(Exception e) {}	
		    }else if((field.getType() == Byte.class)||(field.getType() == byte.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Byte.parseByte((returnString(unitBytes))));
		    	}catch(Exception e) {}	
		    }else if((field.getType() == Float.class)||(field.getType() == float.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, Float.parseFloat((returnString(unitBytes))));
				}catch(Exception e) {}		
		    }else if((field.getType() == Character.class)||(field.getType() == char.class)) {
		    	try {
			    	unitBytes = readBytes(data);
					field.set(anInstance, (returnString(unitBytes)).charAt(0));
		    	}catch(Exception e) {}
		    }else if(field.getType() == Instant.class) {
		    	try {
			    	unitBytes = readBytes(data);
					long l = Long.parseLong((returnString(unitBytes)));
					field.set(anInstance, Instant.ofEpochSecond(l));
		    	}catch(Exception e) {}
		    }else if(field.getType() == BigDecimal.class){
		    	try {
					unitBytes = readBytes(data);
					field.set(anInstance, (new BigDecimal(returnString(unitBytes))));
				}catch(Exception e) {}
		    }else if(field.getType() == List.class) {
		    	try {
		    		List<Object> list = new ArrayList<Object>();
		    		while(data[d_index] != 0) {
		    			list.add(deserialize(data));
		    		}
		    		field.set(anInstance, list);
		    		d_index++;
		    	}catch(Exception e) {System.out.println(e);}
		    }else if(field.getType() == Set.class){
		    	try {
		    		List<Object> list = new ArrayList<Object>();
		    		while(data[d_index] != 0) {
		    			list.add(deserialize(data));
		    		}
		    		Set<Object> set = new HashSet<Object>();
		    		for (int i = list.size()-1; i >= 0; i--) {
		    			set.add(list.get(i));
		    		}
		    		field.set(anInstance, set);
		    		d_index++;
				}catch(Exception e) {}
		    }else if(field.getType() == Map.class){
		    	try {
		    		Map<Object, Object> map = new HashMap<Object, Object>();
		    		while(data[d_index] != 0) {
		    			Object key = deserialize(data);
		    			Object value = deserialize(data);
		    			map.put(key, value);
		    		}
		    		field.set(anInstance, map);
		    		d_index++;
				}catch(Exception e) {}
		    }else {
		    	field.set(anInstance, deserialize(data, field.getType()));
		    }
		}
    } catch (NoSuchFieldException |InstantiationException  | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException  e) {
        e.printStackTrace();
    }
	d_index++;
    return anInstance;
} 

private void fieldsSerialization(Object anInstance, ArrayList<Byte> arrayBefore) //кодирование полей класса
        throws IllegalAccessException, UnsupportedEncodingException {
	for (Field field : anInstance.getClass().getDeclaredFields()) {
		field.setAccessible(true); 
		if((!Modifier.isTransient(field.getModifiers()))&&(!Modifier.isStatic(field.getModifiers()))&&(field.get(anInstance)!= null)) { 
	    if ((field.getType() == Integer.class)||(field.getType() == int.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Integer)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if(field.get(anInstance) instanceof String) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((String)field.get(anInstance)).getBytes(ENCODING),  arrayBefore);	    	
	    }else if((field.getType() == Double.class)||(field.getType() == double.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Double)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Boolean.class)||(field.getType() == boolean.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Boolean)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Short.class)||(field.getType() == short.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Short)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Long.class)||(field.getType() == long.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Long)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Byte.class)||(field.getType() == byte.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Byte)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Float.class)||(field.getType() == float.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Float)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if((field.getType() == Character.class)||(field.getType() == char.class)) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Character)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if(field.getType() == Instant.class) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((Long)((Instant)field.get(anInstance)).getEpochSecond()).toString().getBytes(ENCODING), arrayBefore);
	    }else if(field.getType() == BigDecimal.class) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	addBytes(((BigDecimal)field.get(anInstance)).toString().getBytes(ENCODING),  arrayBefore);
	    }else if(field.get(anInstance) instanceof List) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	for(Object jb : (List<?>)field.get(anInstance)) {
				serialize(jb, jb.getClass().getCanonicalName(), arrayBefore);
	    	}
	    	arrayBefore.add((byte)0);
	    }else if(field.get(anInstance) instanceof Set) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	for(Object jb : (Set<?>)field.get(anInstance)) {
					serialize(jb, jb.getClass().getCanonicalName(), arrayBefore);
	    	}
	    	arrayBefore.add((byte)0);
	    }else if(field.get(anInstance) instanceof Map) {
	    	addBytes((field.getName()).toString().getBytes(ENCODING),  arrayBefore);
	    	for (Map.Entry<Object, Object> entry : ((Map<Object, Object>)field.get(anInstance)).entrySet()) {
				serialize(entry.getKey(), entry.getKey().getClass().getCanonicalName(), arrayBefore);
				serialize(entry.getValue(), entry.getValue().getClass().getCanonicalName(), arrayBefore);         
	        }
	    	arrayBefore.add((byte)0);
	    }else {
	    	try {
				serialize(field.get(anInstance), field.getName().toString(), arrayBefore);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
		}
    }
}

private void addBytes(byte[] bytes, ArrayList<Byte> arrayBefore) { //добавляет байты единицы информации к общему списку байтов
	int length = bytes.length;
	if(length <= max) {//если меньше или равен 127
		arrayBefore.add((byte)length);
		for (byte b : bytes) {
			arrayBefore.add(b);
		}
		if(length == max)arrayBefore.add((byte)0);
	}else {
		arrayBefore.add((byte)max);
		byte[] overflow = new byte[length - max];
		for(int i = 0; i < length; i++) {
			if(i < max) {
				arrayBefore.add(bytes[i]);
			}
			else { 
				overflow[i-max] = bytes[i];
			}			
		}
		addBytes(overflow, arrayBefore);
	}
}

public boolean isCircular(Object anInstance) throws IllegalAccessException {
	for(int i = 0; i < stack.size(); i++) {
		if(stack.get(i) == anInstance){
			return true;
		}
	}
	stack.add(anInstance);
	int index = stack.size()-1;
	Class classObject = anInstance.getClass();
    var fields = classObject.getDeclaredFields();
    for (Field field : fields) {
        field.setAccessible(true);
        Object valueOfField = field.get(anInstance);
        if((!Modifier.isTransient(field.getModifiers()))&&(!Modifier.isStatic(field.getModifiers()))&&(field.get(anInstance)!= null)) { 
    	    if ((field.getType() == Integer.class)||(field.getType() == int.class)) {
    	    	
    	    }else if(field.get(anInstance) instanceof String) {
    	    	
    	    }else if((field.getType() == Double.class)||(field.getType() == double.class)) {
    	    	
    	    }else if((field.getType() == Boolean.class)||(field.getType() == boolean.class)) {
    	    	
    	    }else if((field.getType() == Short.class)||(field.getType() == short.class)) {
    	    	
    	    }else if((field.getType() == Long.class)||(field.getType() == long.class)) {
    	    	
    	    }else if((field.getType() == Byte.class)||(field.getType() == byte.class)) {
    	    
    	    }else if((field.getType() == Float.class)||(field.getType() == float.class)) {
    	    	
    	    }else if((field.getType() == Character.class)||(field.getType() == char.class)) {
    	    	
    	    }else if(field.getType() == Instant.class) {
    	    	
    	    }else if(field.getType() == BigDecimal.class) {
    	    	
    	    }else if(field.get(anInstance) instanceof List) {
    	    	for(Object jb : (List<?>)field.get(anInstance)) {
    	    		if(isCircular(jb)) return true;
    	    	}
    	    	
    	    }else if(field.get(anInstance) instanceof Set) {
    	    	for(Object jb : (Set<?>)field.get(anInstance)) {
    	    		if(isCircular(jb)) return true;
    	    	}
    	    }else if(field.get(anInstance) instanceof Map) {
    	    	for (Map.Entry<Object, Object> entry : ((Map<Object, Object>)field.get(anInstance)).entrySet()) {
    	    		if(isCircular(entry.getKey())) return true;
    	    		if(isCircular(entry.getValue())) return true;
    	        }
    	    }else {
    	    	if(field.get(anInstance) != anInstance) {
    	    		if(isCircular(field.get(anInstance))) return true;
    	    	}else return true;
    	    }
        }
        }
    for(int i = index; i < stack.size(); i++) {
    	stack.remove(i);
	}
    return false;
}


}
