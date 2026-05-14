import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private BASE_URL = 'http://localhost:8080/auth';

  constructor(private http: HttpClient, private router: Router) {}

  register(data: any) {
    return this.http.post(`${this.BASE_URL}/register`, data);
  }

 login(data: any) {
  return this.http.post<any>(`${this.BASE_URL}/login`, data);
}

  saveToken(response: any) {

  localStorage.setItem("token", response.token);

  localStorage.setItem("role", response.role);
}

  getToken(): string | null {
    return localStorage.getItem("token");
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

 logout() {

  localStorage.removeItem("token");

  localStorage.removeItem("role");

  this.router.navigate(['/login']);
}
  getRole(): string | null {
  return localStorage.getItem("role");
}

isAdmin(): boolean {
  return this.getRole() === 'ROLE_ADMIN';
}
}