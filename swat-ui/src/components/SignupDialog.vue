<template>
    <v-dialog v-model="dialog" persistent max-width="500px">
      <v-card>
        <v-card-title class="text-h5">Sign Up</v-card-title>
        <v-card-text>
          <v-form ref="form" v-model="valid" @submit.prevent="register">
            <v-text-field
              v-model="username"
              :rules="[rules.required, rules.minLength(3)]"
              label="Username"
              required
              autofocus
            ></v-text-field>
            <v-text-field
              v-model="email"
              :rules="[rules.required, rules.email]"
              label="Email"
              required
            ></v-text-field>
            <v-text-field
              v-model="password"
              :rules="[rules.required, rules.minLength(6)]"
              label="Password"
              type="password"
              required
            ></v-text-field>
            <v-text-field
              v-model="confirmPassword"
              :rules="[rules.required, rules.matchPassword]"
              label="Confirm Password"
              type="password"
              required
            ></v-text-field>
            <v-btn color="primary" type="submit" :disabled="!valid">
              Register
            </v-btn>
            <v-btn text @click="dialog = false">Cancel</v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useAppStore } from '@/stores/app';
  
  const store = useAppStore();
  const dialog = ref(false);
  const username = ref('');
  const email = ref('');
  const role = ref(['ROLE_USER']);
  const password = ref('');
  const confirmPassword = ref('');
  const valid = ref(false);
  
  const rules = {
    required: value => !!value || 'Required.',
    minLength: min => value => value.length >= min || `Minimum ${min} characters required.`,
    email: value => /.+@.+\..+/.test(value) || 'E-mail must be valid.',
    matchPassword: () => password.value === confirmPassword.value || 'Passwords must match.'
  };
  
  const register = async () => {
    if (valid.value) {
      const credentials = { email: email.value, username: username.value, password: password.value, roles: role.value }

      const success = await store.signup(credentials);

      if(success) {
        dialog.value = false;
        store.showSnackbar('Account created, you may now login!', 'success');
      }
      else {
        store.showSnackbar('Error registering, please contact an admin if this issue persists.', 'error');
      }
    }
  };
  </script>
  
  <style scoped>
  </style>