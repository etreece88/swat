<template>
  <v-row class="mb-2">
    <v-col cols="3">
      <v-text-field
        v-model="search"
        label="Search"
        prepend-inner-icon="mdi-magnify"
        variant="outlined"
        hide-details
        single-line
        color="secondary"
      ></v-text-field>
    </v-col>
  </v-row>

  <v-data-table
    :headers="headers"
    :items="items"
    :search="search"
    class="elevation-5"
    height="70vh"
    fixed-header
  >
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon
        v-if="item.username !== user.username"
        class="me-2"
        size="small"
        color="primary"
        @click="toggleEdit(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon v-if="item.username !== user.username" size="small" color="error"  @click="toggleDelete(item)">
        mdi-delete
      </v-icon>
      <v-dialog v-model="dialogEdit" max-width="500px">
        <v-card
          class="d-flex flex-column align-center justify-center"
          height="250px"
        >
          <v-card-title class="text-center">
            <v-card-text class="title">
              Editing role for <a class="username">{{ editUser.username }}</a>
            </v-card-text>
          </v-card-title>
          <v-card-actions
            class="text-center d-flex flex-column align-center justify-center"
          >
            <v-row>
              <v-col cols="12" sm="6">
                <v-select
                  v-model="role"
                  :items="roles"
                  label="Select Role"
                  item-value="role"
                  item-text="notificationType"
                  color="secondary"
                  return-object
                  width="250px"
                >
                </v-select>
              </v-col>
            </v-row>
            <v-row>
              <v-col cols="12" sm="6">
                <v-btn color="error" variant="text" @click="updateRole"
                  >Save</v-btn
                >
              </v-col>
              <v-col cols="12" sm="6">
                <v-btn color="primary" variant="text" @click="closeEdit"
                  >Cancel</v-btn
                >
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="dialogDelete" max-width="750px" max-height="500px">
        <v-card class="d-flex flex-column align-center justify-center">
          <v-card-title class="text-center">
            <v-card-text class="title">
              Are you sure you want to delete user
              <a class="username">{{ editUser.username }}</a
              >?
            </v-card-text>
          </v-card-title>
          <v-card-actions class="text-center">
            <v-row>
              <v-col cols="12" sm="6">
                <v-btn color="error" variant="text" @click="deleteUser"
                  >Delete</v-btn
                >
              </v-col>
              <v-col cols="12" sm="6">
                <v-btn color="primary" variant="text" @click="closeDelete"
                  >Cancel</v-btn
                >
              </v-col>
            </v-row>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </template>
  </v-data-table>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useAppStore } from "@/stores/app";

const app = useAppStore();

// Table Props
const headers = ref([
  { key: "username", title: "Username", width: "30%" },
  { key: "email", title: "Email", width: "30%" },
  { key: "roleEnums[0]", title: "Role", width: "30%" },
  { key: "actions", title: "Actions", width: "30%" },
]);

const items = ref([]);
const search = ref("");
const dialogDelete = ref(false);
const dialogEdit = ref(false);

const editUser = ref({});
const roles = ref([]);
const role = ref("");
const user = computed(() => app.user);

// Fetch Table data from store, set defaults
async function getData() {
  const token = user.value.token;

  if (token) {
    items.value = await app.getUsers(token);
    const data = await app.getRoles(token);

    roles.value = data.map((item) => item.name);
  } else {
    console.log("Unable to authenticate user.");
  }
}

async function updateRole() {
  const user = computed(() => app.user);
  const token = user.value.token;

  const res = await app.changeRole(token, editUser.value.username, role.value);

  if (res == 200) {
    app.showSnackbar(
      "Role changed for '" + editUser.value.username + "'!",
      "primary"
    );
  } else {
    app.showSnackbar("Unable to change role", "error");
  }

  getData();
  closeEdit();
}

async function deleteUser() {
  const user = computed(() => app.user);
  const token = user.value.token;

  const res = await app.deleteUser(token, editUser.value.username);

  if (res == 200) {
    app.showSnackbar(
      "User  '" + editUser.value.username + "' has been deleted!",
      "error"
    );
  } else {
    app.showSnackbar("Unable to delete user", "error");
  }

  getData();
  closeDelete();
}

const toggleEdit = (item) => {
  editUser.value = item;
  dialogEdit.value = true;
};

const toggleDelete = (item) => {
  editUser.value = item;
  dialogDelete.value = true;
};

const closeEdit = () => {
  dialogEdit.value = false;
};

const closeDelete = () => {
  dialogDelete.value = false;
};

onMounted(() => {
  getData();
});
</script>

<style scoped>
.username {
  color: red !important;
  font-weight: bold;
}

.title {
  font-size: large;
}
</style>
