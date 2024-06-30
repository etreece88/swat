<template>
    <v-card variant="tonal" dense color="primary">
        <v-card-title style="color: #8d5b4c !important" class="text-center">NOAA Storm Levels</v-card-title>
        <container fluid class="fill-height d-flex align-center justify-center">
            <v-row class="mb-2 mx-1">
                <v-col cols="12" sm="4">
                    <v-card :color="getColor(currCon.G.Scale)" variant="elevated"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-title class="text-center" style="color: white !important">Geomagnetic Storm</v-card-title>
                        <v-card-text>
                            <p style="color: white !important">G: {{ currCon.G.Scale }}</p>
                        </v-card-text>
                    </v-card>
                </v-col>
                <v-col cols="12" sm="4">
                    <v-card :color="getColor(currCon.G.Scale)" variant="elevated"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-title class="text-center" style="color: white !important">Radiation Storm</v-card-title>
                        <v-card-text>
                            <p style="color: white !important">
                                S: {{ currCon.S.Scale }}
                            </p>
                        </v-card-text>
                    </v-card>
                </v-col>
                <v-col cols="12" sm="4">
                    <v-card :color="getColor(currCon.G.Scale)" variant="elevated"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-title class="text-center" style="color: white !important">Radio Blackout</v-card-title>
                        <v-card-text>
                            <p style="color: white !important">
                                R: {{ currCon.R.Scale }}
                            </p>
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

const currCon = computed(() => props.data);

const getColor = (scale) => {
    switch (scale) {
        case "5":
            return "red !important";
        case "4":
            return "orange !important";
        case "3":
            return "yellow !important";
        case "2":
            return "green !important";
        case "1":
            return "light-green !important";
        default:
            return "light-green !important";
    }
};
</script>

<style scoped>
.login-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}
</style>