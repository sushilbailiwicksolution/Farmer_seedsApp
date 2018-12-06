package farmer.bailiwick.seeds.pathein.patheinseedslaboratory.Support;

/**
 * Created by Prince on 30-11-2018.
 */

public class CalulateMethods {

    // Moisture  testing Meter Testing

    public static float getMeterPercent(String meter1, String meter2, String meter3) {
        float value = 0.0f;
        float M1 = Float.parseFloat(meter1);
        float M2 = Float.parseFloat(meter2);
        float M3 = Float.parseFloat(meter3);

        value = (M1 + M2 + M3) / 3;

        return value;

    }
    // Moisture  testing OVEN Testing

    public static float getOvenPercent(float wt_c, float wt_c_seed, float wt_c_dry_seed) {
        float value = 0.0f;
        value = (wt_c_seed - wt_c_dry_seed) / (wt_c_dry_seed - wt_c) * 100;
        return value;
    }

    // Physical testing Pure seed Average
    public static float getPureSeedAverage(String pure_seed, String seed_weight) {
        float value = 0.0f;
        float pure = Float.parseFloat(pure_seed);
        float weight = Float.parseFloat(seed_weight);

        value = (pure / weight) * 100;

        return value;

    }
    // Physical testing Inert Matter seed Average

    public static float getInertSeedAverage(String inert_seed, String seed_weight) {
        float value = 0.0f;
        float inertMatter = Float.parseFloat(inert_seed);
        float weight = Float.parseFloat(seed_weight);

        value = (inertMatter / weight) * 100;

        return value;

    }

    // Physical testing other Matter seed Average
    public static float getOtherSeedAverage(String other_seed, String seed_weight) {
        float value = 0.0f;
        float otherMatter = Float.parseFloat(other_seed);
        float weight = Float.parseFloat(seed_weight);
        value = (otherMatter / weight) * 100;
        return value;
    }

    // Red Rice seed Average 500
    public static float getRED_RICEAverage(String rr_wrkng_sample, String seed_weight_ws) {
        float value = 0.0f;
        float working_sample = Float.parseFloat(seed_weight_ws);
        float red_rice_working = Float.parseFloat(rr_wrkng_sample);
        value = (red_rice_working * 500) / working_sample;
        return value;
    }


    public static float getAverageWEIGHT(String seed, String seed1) {
        float value = 0.0f;
        float seed_1 = Float.parseFloat(seed);
        float seed_2 = Float.parseFloat(seed1);

        value = (seed_1 + seed_2) / 2;
        return value;
    }
}