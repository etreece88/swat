<template>
  <v-app>
    <v-app-bar app class="flex text-center">
      <v-toolbar-title class="justify-center align-center">
        <v-avatar image="@/assets/swat3.png" class="logo mr-1"></v-avatar>
        Space Weather Alerts and Tracking
      </v-toolbar-title>
    </v-app-bar>
      <div v-if="isLoading" class="loading-overlay">
        <v-progress-circular indeterminate color="icon" size="64"></v-progress-circular>
      </div>

    <div class="stars" />

    <v-container class="fill-height d-flex align-center justify-center">
      <v-card
        width="60vw"
        height="50vh"
        class="d-flex flex-column align-center justify-center login-card"
      >
        <v-card-title class="text-center mt-5">Login</v-card-title>
        <v-card-text class="d-flex flex-column align-center justify-center">
          <v-form
            ref="form"
            v-model="valid"
            class="d-flex flex-column align-center justify-center"
          >
            <v-text-field
              v-model="username"
              :rules="[rules.required]"
              label="Username"
              required
              class="mb-4"
              width="30vw"
              color="black"
              bg-color="#726c00"
            ></v-text-field>
            <v-text-field
              v-model="password"
              :rules="[rules.required]"
              label="Password"
              type="password"
              required
              class="mb-4"
              width="30vw"
              color="black"
              bg-color="#726c00"
            ></v-text-field>
            <v-row>
              <v-btn color="success" @click="login" class="mr-1"> <a style="color: black !important">Login</a> </v-btn>
              <v-btn color="icon" @click="openSignup" class=""> Sign Up </v-btn>
            </v-row>
          </v-form>
        </v-card-text>
      </v-card>
      <v-dialog
        v-model="dialog"
        persistent
        max-width="600px"
        max-height="600px"
      >
        <v-card class="d-flex flex-column align-center justify-center" varient="elevated">
          <v-card-title class="text-h5 text-center mt-5 pb-0"
            >Sign Up</v-card-title
          >
          <v-card-text class="d-flex flex-column align-center justify-center">
            <v-form ref="form" v-model="sValid" @submit.prevent="register">
              <v-text-field
                v-model="sUsername"
                :rules="[sRules.required, sRules.minLength(3)]"
                label="Username"
                required
                bg-color="#726c00"
              ></v-text-field>
              <v-text-field
                v-model="email"
                :rules="[sRules.required, sRules.email]"
                label="Email"
                required
                bg-color="#726c00"
              ></v-text-field>
              <v-text-field
                v-model="sPassword"
                :rules="[sRules.required, sRules.minLength(6)]"
                label="Password"
                type="password"
                required
                bg-color="#726c00"
              ></v-text-field>
              <v-text-field
                v-model="confirmPassword"
                :rules="[sRules.required, sRules.matchPassword]"
                label="Confirm Password"
                type="password"
                bg-color="#726c00"
                required
              ></v-text-field>
              <v-btn
                color="success"
                class="mr-1"
                type="submit"
                :disabled="!sValid"
              >
               <a style="color: black !important">Register</a>
              </v-btn>
              <v-btn color="error" text @click="dialog = false">Cancel</v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-container>
  </v-app>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAppStore } from "@/stores/app";
import { storeToRefs } from "pinia";

const store = useAppStore();
const router = useRouter();

const { isLoading } = storeToRefs(store);
const username = ref("");
const password = ref("");
const valid = ref(false);
const rules = {
  required: (value) => !!value || "Required.",
};

const dialog = ref(false);
const sUsername = ref("");
const email = ref("");
const role = ref(["ROLE_USER"]);
const sPassword = ref("");
const confirmPassword = ref("");
const sValid = ref(false);

const sRules = {
  required: (value) => !!value || "Required.",
  minLength: (min) => (value) =>
    value.length >= min || `Minimum ${min} characters required.`,
  email: (value) => /.+@.+\..+/.test(value) || "E-mail must be valid.",
  matchPassword: () =>
    sPassword.value === confirmPassword.value || "Passwords must match.",
};

const register = async () => {
  if (sValid.value) {
    const credentials = {
      email: email.value,
      username: sUsername.value,
      password: sPassword.value,
      roles: role.value,
    };

    const success = await store.signup(credentials);

    if (success) {
      dialog.value = false;
      store.showSnackbar("Account created, you may now login!", "primary");
    } else {
      store.showSnackbar(
        "Error registering, please contact an admin if this issue persists.",
        "error"
      );
    }
  }
};

const login = async () => {
  store.loading(true);
  if (valid.value) {
    const credentials = { username: username.value, password: password.value };
    const success = await store.login(credentials);

    if (success) {
      await store.loadUserFromSession();
      router.push("/");
    } else {
      store.showSnackbar(
        "Login failed, contact an admin for further assistance.",
        "error"
      );
    }
  } else {
    store.showSnackbar("Please enter username and password", "error");
  }
  store.loading(false);
};

const openSignup = () => {
  dialog.value = true;
};
</script>

<style scoped>
.fill-height {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.login-card {
  border: 1px solid black;
  border-radius: 1%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.v-card {
  z-index: 1;
}

@keyframes starfield {
  from {
    transform: translateY(0);
  }

  to {
    transform: translateY(-2000px);
  }
}

.stars {
  width: 100%;
  height: 100%;
  background: transparent;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  overflow: hidden;
}

.stars::before,
.stars::after {
  content: "";
  position: absolute;
  top: 100%;
  width: 2px;
  height: 2px;
  border-radius: 50%;
  background: #8d5b4c;
  box-shadow: 0 0, 1523px 398px #8d5b4c, 1778px 1104px #8d5b4c,
    911px 1463px #8d5b4c, 139px 1852px #8d5b4c, 1248px 587px #8d5b4c,
    669px 1730px #8d5b4c, 1810px 290px #8d5b4c, 1321px 875px #8d5b4c,
    1945px 743px #8d5b4c, 1038px 1284px #8d5b4c, 349px 1672px #8d5b4c,
    589px 1591px #8d5b4c, 1432px 1920px #8d5b4c, 1587px 128px #8d5b4c,
    489px 1012px #8d5b4c, 764px 1398px #8d5b4c, 1756px 596px #8d5b4c,
    290px 145px #8d5b4c, 178px 1897px #8d5b4c, 1158px 864px #8d5b4c,
    921px 1145px #8d5b4c, 1537px 356px #8d5b4c, 154px 542px #8d5b4c,
    1742px 197px #8d5b4c, 1329px 1129px #8d5b4c, 809px 622px #8d5b4c,
    1620px 1680px #8d5b4c, 493px 1512px #8d5b4c, 189px 780px #8d5b4c,
    1823px 239px white, 1231px 1628px white, 187px 1907px white,
    1459px 472px white, 104px 1329px white, 1697px 137px white,
    512px 1845px white, 932px 762px white, 1483px 923px white,
    1121px 1704px white, 279px 1576px white, 1894px 539px white,
    158px 1538px white, 1372px 876px white, 1254px 145px white,
    723px 1362px white, 1739px 621px white, 904px 1911px white,
    346px 493px white, 1632px 288px white, 1159px 1817px white,
    1964px 327px white, 1423px 102px white, 499px 1320px white,
    1754px 1592px white, 1028px 1193px white;
  animation: starfield 50s linear infinite;
}

.stars::after {
  top: 0;
  animation-delay: 0s;
}
</style>