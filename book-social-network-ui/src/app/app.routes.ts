import { Routes } from '@angular/router';

import { AuthGuard } from './guards/auth-guard';
import { AddBook } from './books/add-book/add-book';
import { BookList } from './books/book-list/book-list';
import { Register } from './auth/register/register';
import { Login } from './auth/login/login';
import { Activate } from './auth/activate/activate';

export const routes: Routes = [
  { path: '', redirectTo: 'books', pathMatch: 'full' },

  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'activate', component: Activate },

  {
    path: 'books',
    component: BookList,
    canActivate: [AuthGuard]
  },

  {
    path: 'add-book',
    component: AddBook,
    canActivate: [AuthGuard]
  },
 {
  path: 'edit-book/:id',
  loadComponent: () =>
    import('./books/edit-book/edit-book').then(m => m.EditBook),
  canActivate: [AuthGuard]
},
];