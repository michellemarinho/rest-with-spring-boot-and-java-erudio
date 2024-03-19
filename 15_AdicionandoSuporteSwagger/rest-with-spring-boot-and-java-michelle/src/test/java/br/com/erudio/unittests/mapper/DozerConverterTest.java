package br.com.erudio.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.model.Person;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import br.com.erudio.unittests.mapper.mocks.MockPerson;

public class DozerConverterTest {
    
    Object inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToVOTest() {
        PersonVO output = DozerMapper.parseObject(inputObject.mockEntity(), PersonVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<PersonVO> outputList = DozerMapper.parseListObject(inputObject.mockEntity(), PersonVO.class);
        PersonVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Addres Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        PersonVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Addres Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        
        PersonVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Addres Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    @Test
    public void parseVOToEntityTest() {
        Person output = DozerMapper.parseObject(inputObject.mockVO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Addres Test0", output.getAddress());
        assertEquals("Male", output.getGender());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Person> outputList = DozerMapper.parseListObject(inputObject.mockVO(), Person.class);
        Person outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Addres Test0", outputZero.getAddress());
        assertEquals("Male", outputZero.getGender());
        
        Person outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Addres Test7", outputSeven.getAddress());
        assertEquals("Female", outputSeven.getGender());
        
        Person outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Addres Test12", outputTwelve.getAddress());
        assertEquals("Male", outputTwelve.getGender());
    }

    	//Classe de teste alterada daqui pra baixo para incluir book
    @BeforeEach
    public void setUp2() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTestB() {
        BookVO output = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Name Book Test13", output.getBookName());
        assertEquals("Descrição Book Test13", output.getDescBook());
        assertEquals("Preço Test13", output.getPreco());
    }

    @Test
    public void parseEntityListToVOListTestB() {
        List<BookVO> outputList = DozerMapper.parseListObject(inputObject.mockEntity(), BookVO.class);
        BookVO outputZero = outputList.get(13);
        
        assertEquals(Long.valueOf(13L), outputZero.getKey());
        assertEquals("Name Book Test13", outputZero.getBookName());
        assertEquals("Descrição Book Test13", outputZero.getDescBook());
        assertEquals("Preço Test13", outputZero.getPreco());
        
        BookVO outputSeven = outputList.get(8);
        
        assertEquals(Long.valueOf(8L), outputSeven.getKey());
        assertEquals("Name Book Test8", outputSeven.getBookName());
        assertEquals("Descrição Book Test8", outputSeven.getDescBook());
        assertEquals("Preço Test8", outputSeven.getPreco());
        
        BookVO outputTwelve = outputList.get(10);
        
        assertEquals(Long.valueOf(10L), outputTwelve.getKey());
        assertEquals("Name Book Test10", outputTwelve.getBookName());
        assertEquals("Descrição Book Test10", outputTwelve.getDescBook());
        assertEquals("Preço Test10", outputTwelve.getPreco());
    }

    @Test
    public void parseVOToEntityTestB() {
    	Book output = DozerMapper.parseObject(inputObject.mockVO(), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Book Test13", output.getBookName());
        assertEquals("Descrição Book Test13", output.getDescBook());
        assertEquals("Preço Test13", output.getPreco());
    }

    @Test
    public void parserVOListToEntityListTestB() {
        List<Book> outputList = DozerMapper.parseListObject(inputObject.mockVO(),Book.class);
        Book outputZero = outputList.get(13);
        
        assertEquals(Long.valueOf(8L), outputZero.getId());
        assertEquals("Name Book Test13", outputZero.getBookName());
        assertEquals("Descrição Book Test13", outputZero.getDescBook());
        assertEquals("Preço Test13", outputZero.getPreco());
        
        Book outputSeven = outputList.get(8);
        
        assertEquals(Long.valueOf(8L), outputSeven.getId());
        assertEquals("Name Book Test8", outputSeven.getBookName());
        assertEquals("Descrição Book Test8", outputSeven.getDescBook());
        assertEquals("Preço Test8", outputSeven.getPreco());
        
        Book outputTwelve = outputList.get(10);
        
        assertEquals(Long.valueOf(10L), outputTwelve.getId());
        assertEquals("Name Book Test10", outputTwelve.getBookName());
        assertEquals("Descrição Book Test10", outputTwelve.getDescBook());
        assertEquals("Preço Test10", outputTwelve.getPreco());
    }
}
