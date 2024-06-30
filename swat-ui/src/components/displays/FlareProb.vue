<template>
    <v-card variant="tonal" dense color="primary" class="flr-card">
        <v-card-title style="color: #8d5b4c !important" class="text-center">X-Class Probability</v-card-title>
        <container fluid class="fill-height d-flex align-center justify-center">
            <v-row class="mb-2 mx-1">
                <v-col>
                    <v-card variant="tonal"
                        class="my-2 mx-2 d-flex flex-column align-center justify-center login-card">
                        <v-card-text class="">
                            <p style="color: white !important">{{ slrPrb.x_class_1_day }}%</p>
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

const slrPrb = computed(() => props.data);


</script>
<style scoped>
.login-card {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.flr-card {
    margin-top: 30px;
}
</style>