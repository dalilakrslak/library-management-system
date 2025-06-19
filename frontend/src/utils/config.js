const API_BASE_URL = 'http://localhost:8080';

const BASES = {
  library: 'http://localhost:8080/library-service',
  book: 'http://localhost:8080/book-service',
  transfer: 'http://localhost:8080/transfer-service',
};

const ROUTES = {
  login: `${BASES.library}/auth/login`,
  libraries: `${BASES.library}/library`,
  users: `${BASES.library}/user`,
  books: `${BASES.book}/book`,
  authors: `${BASES.book}/author`,
  genres: `${BASES.book}/genre`,
  roles: `${BASES.library}/role`,
  booksByLibrary: (libraryId) => `${BASES.book}/book/books/library/${libraryId}`,
  reservation: `${BASES.book}/reservation`,
  bookVersions: `${BASES.book}/book-version`,
  loan: `${BASES.book}/loan`,
  transfer: `${BASES.transfer}/transfer`,
  availability: `${BASES.book}/book/availability`,
};

export { API_BASE_URL, ROUTES };
