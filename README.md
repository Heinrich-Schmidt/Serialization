# Serialization

Необходимо написать класс который сериализует/десериализует Java Beans.

В качестве полей в Bean могут быть:
     простые типы, 
     String, Instant, BigDecimal и все классы обертки для простых типов (Short, Integer, Long и т.д.)
     Beans, 
     коллекции Beans: List, Map, Set

В случае наличия циклических ссылок выкинуть exception. Класс должен имплементировать следующий интерфейс:

interface SuperEncoder {
        byte[] serialize(Object anyBean);
        Object deserialize(byte[] data);  

При выполнении задания нельзя использовать готовые сериализаторы, такие как JSON, Protobuf, Java serialization API.
