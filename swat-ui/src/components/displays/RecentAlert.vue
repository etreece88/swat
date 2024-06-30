<template>
    <v-card variant="tonal" dense color="error" prepend-icon="mdi-alert" title="Alert!">
        <container fluid class="fill-height d-flex align-center justify-center">
            <v-row class="mb-2 mx-1">
                <v-col>
                    <v-card variant="tonal"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-text class="">
                            {{ alert.product_id + ' - ' + alert.issue_datetime }}
                            <p style="color: white !important">{{ alert.message }}</p>
                        </v-card-text>
                    </v-card>
                </v-col>
            </v-row>
        </container>
    </v-card>
</template>
<script setup>

import { defineProps, computed } from "vue";

const props = defineProps({
    data: {
        type: Object,
        required: true,
    },
});

const alert = computed(() => props.data);



</script>
<style scoped>
.login-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>