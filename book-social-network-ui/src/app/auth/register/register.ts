import { Component } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  imports: [FormsModule,CommonModule,RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
   fullName: string = '';
  email: string = '';
  password: string = '';

  constructor(private auth: AuthService) {}

  register() {
  this.auth.register({
    fullName: this.fullName,
    email: this.email,
    password: this.password
  }).subscribe({
    next: (res: any) => {
      console.log(res);
      alert(res || 'Registration successful. Check email for activation.');
    },
    error: (err) => {
      console.log('Backend error:', err);

      const msg =
        err?.error?.message ||
        err?.error ||
        'Registration failed';

      alert(msg);
    }
  });
}
}
