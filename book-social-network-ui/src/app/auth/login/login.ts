import { Component } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
    email: string = '';
  password: string = '';

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    this.auth.login({ email: this.email, password: this.password })
      .subscribe({
        next: (response: any) => {
          this.auth.saveToken(response);
          this.router.navigate(['/books']);
        },
        error: (err) => {
          alert('Invalid email or password');
        }
      });
  }
}
