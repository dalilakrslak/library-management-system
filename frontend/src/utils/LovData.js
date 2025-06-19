import { ROUTES } from './config';

const getTokenHeader = () => {
  const token = localStorage.getItem('token');
  return {
    Authorization: `Bearer ${token}`
  };
};

export const fetchAuthorMap = async () => {
  const res = await fetch(ROUTES.authors, {
    headers: getTokenHeader()
  });
  const data = await res.json();

  const map = {};
  data.forEach(author => {
    map[author.id] = `${author.firstName} ${author.lastName}`;
  });
  return map;
};

export const fetchGenreMap = async () => {
  const res = await fetch(ROUTES.genres, {
    headers: getTokenHeader()
  });
  const data = await res.json();

  const map = {};
  data.forEach(genre => {
    map[genre.id] = genre.name;
  });
  return map;
};

export const fetchLibraryMap = async () => {
  const res = await fetch(ROUTES.libraries, {
    headers: getTokenHeader()
  });
  const data = await res.json();

  const map = {};
  data.forEach(lib => {
    map[lib.id] = lib.name;
  });
  return map;
};

export const fetchRoleMap = async () => {
  const res = await fetch(ROUTES.roles, {
    headers: getTokenHeader()
  });
  const data = await res.json();

  const map = {};
  data.forEach(role => {
    map[role.id] = role.name;
  });
  return map;
};
