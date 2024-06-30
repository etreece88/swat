<template>
    <v-card variant="tonal" dense color="primary">
        <v-card-title style="color: #8d5b4c !important" class="text-center">
            Wind Conditions
        </v-card-title>
        <container fluid class="fill-height d-flex align-center justify-center">
            <v-row>
                <v-col cols="12" sm="4">
                    <v-card-text class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                            <p style="color: white !important">Speed: {{ currWinds.slrWinds[1] }}km/s</p>
                    </v-card-text>
                </v-col>
                <v-col cols="12" sm="4">
                    <v-card-text class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                            <p style="color: white !important">Density: {{ currWinds.slrWinds[2] }}kWh/m2</p>
                    </v-card-text>
                </v-col>
                <v-col cols="12" sm="4">
                    <v-card-text class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                            <p style="color: white !important">Temperature: {{ currWinds.slrWinds[3] }}°C</p>
                    </v-card-text>
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

const currWinds = computed(() => props.data);

</script>

<style scoped>
.login-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>