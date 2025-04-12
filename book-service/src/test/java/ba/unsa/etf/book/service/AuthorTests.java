package ba.unsa.etf.book.service;

import ba.unsa.etf.book.api.model.Author;
import ba.unsa.etf.book.core.impl.AuthorServiceImpl;
import ba.unsa.etf.book.core.mapper.AuthorMapper;
import ba.unsa.etf.book.core.validation.AuthorValidation;
import ba.unsa.etf.book.dao.model.AuthorEntity;
import ba.unsa.etf.book.dao.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorTests {

	@Mock
	private AuthorRepository authorRepository;

	@Mock
	private AuthorMapper authorMapper;

	@Mock
	private AuthorValidation authorValidation;

	@InjectMocks
	private AuthorServiceImpl authorService;

	private AuthorEntity authorEntity;
	private Author authorDto;

	@BeforeEach
	void setUp() {
		authorEntity = new AuthorEntity(1L, "John", "Doe", "Biography");
		authorDto = new Author(1L, "John", "Doe", "Biography");
	}

	@Test
	void testFindAll() {
		// Arrange
		List<AuthorEntity> authorEntities = List.of(
				new AuthorEntity(1L, "John", "Doe", "Biography 1"),
				new AuthorEntity(2L, "Jane", "Smith", "Biography 2")
		);

		List<Author> authorDtos = List.of(
				new Author(1L, "John", "Doe", "Biography 1"),
				new Author(2L, "Jane", "Smith", "Biography 2")
		);

		when(authorRepository.findAll()).thenReturn(authorEntities);
		when(authorMapper.entityToDto(authorEntities.get(0))).thenReturn(authorDtos.get(0));
		when(authorMapper.entityToDto(authorEntities.get(1))).thenReturn(authorDtos.get(1));

		// Act
		List<Author> result = authorService.findAll();

		// Assert
		assertNotNull(result);
		assertEquals(2, result.size());

		assertEquals(authorDtos.get(0).getId(), result.get(0).getId());
		assertEquals(authorDtos.get(1).getId(), result.get(1).getId());

		verify(authorRepository).findAll();
		verify(authorMapper, times(2)).entityToDto(any(AuthorEntity.class));
	}


	@Test
	void testFindById_AuthorExists() {
		Long id = 1L;
		when(authorRepository.findById(id)).thenReturn(Optional.of(authorEntity));
		when(authorMapper.entityToDto(authorEntity)).thenReturn(authorDto);

		Author result = authorService.findById(id);

		assertNotNull(result);
		assertEquals(authorDto.getId(), result.getId());
		assertEquals(authorDto.getFirstName(), result.getFirstName());
		assertEquals(authorDto.getLastName(), result.getLastName());
		assertEquals(authorDto.getBiography(), result.getBiography());

		verify(authorValidation).exists(id);
		verify(authorRepository).findById(id);
		verify(authorMapper).entityToDto(authorEntity);
	}

	@Test
	void testFindById_AuthorNotFound() {
		Long id = 2L;
		when(authorRepository.findById(id)).thenReturn(Optional.empty());

		Author result = authorService.findById(id);

		assertNull(result);
		verify(authorValidation).exists(id);
		verify(authorRepository).findById(id);
		verifyNoMoreInteractions(authorMapper);
	}

	@Test
	void testCreate() {
		// Arrange
		Author authorToCreate = new Author(1L, "Jane", "Smith", "Some biography");
		AuthorEntity authorEntity = new AuthorEntity(1L, "Jane", "Smith", "Some biography");
		AuthorEntity savedEntity = new AuthorEntity(1L, "Jane", "Smith", "Some biography");
		Author authorDto = new Author(1L, "Jane", "Smith", "Some biography");

		when(authorMapper.dtoToEntity(authorToCreate)).thenReturn(authorEntity);
		when(authorRepository.save(authorEntity)).thenReturn(savedEntity);
		when(authorMapper.entityToDto(savedEntity)).thenReturn(authorDto);

		// Act
		Author result = authorService.create(authorToCreate);

		// Assert
		assertNotNull(result);
		assertEquals(authorDto.getId(), result.getId());
		assertEquals(authorDto.getFirstName(), result.getFirstName());
		assertEquals(authorDto.getLastName(), result.getLastName());
		assertEquals(authorDto.getBiography(), result.getBiography());

		verify(authorValidation).validateCreate(authorToCreate);
		verify(authorRepository).save(authorEntity);
		verify(authorMapper).dtoToEntity(authorToCreate);
		verify(authorMapper).entityToDto(savedEntity);
	}

	@Test
	void testUpdate_AuthorExists() {
		// Arrange
		Author authorToUpdate = new Author(1L, "John", "Doe", "Updated biography");
		AuthorEntity authorEntity = new AuthorEntity(1L, "John", "Doe", "Updated biography");
		AuthorEntity updatedEntity = new AuthorEntity(1L, "John", "Doe", "Updated biography");
		Author authorDto = new Author(1L, "John", "Doe", "Updated biography");

		when(authorMapper.dtoToEntity(authorToUpdate)).thenReturn(authorEntity);
		when(authorRepository.save(authorEntity)).thenReturn(updatedEntity);
		when(authorMapper.entityToDto(updatedEntity)).thenReturn(authorDto);

		// Act
		Author result = authorService.update(authorToUpdate);

		// Assert
		assertNotNull(result);
		assertEquals(authorDto.getId(), result.getId());
		assertEquals(authorDto.getFirstName(), result.getFirstName());
		assertEquals(authorDto.getLastName(), result.getLastName());
		assertEquals(authorDto.getBiography(), result.getBiography());

		verify(authorValidation).validateUpdate(authorToUpdate);
		verify(authorRepository).save(authorEntity);
		verify(authorMapper).dtoToEntity(authorToUpdate);
		verify(authorMapper).entityToDto(updatedEntity);
	}

	@Test
	void testDelete() {
		// Arrange
		Long id = 1L;

		doNothing().when(authorRepository).deleteById(id);

		// Act
		authorService.delete(id);

		// Assert
		verify(authorValidation).exists(id);
		verify(authorRepository).deleteById(id);
	}
}