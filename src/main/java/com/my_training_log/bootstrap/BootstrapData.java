package com.my_training_log.bootstrap;

import com.my_training_log.entity.Exercice;
import com.my_training_log.entity.Muscle;
import com.my_training_log.entity.MuscleGroup;
import com.my_training_log.repository.ExerciceRepository;
import com.my_training_log.repository.MuscleGroupRepository;
import com.my_training_log.repository.MuscleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    @Autowired
    ExerciceRepository exerciceRepository;

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    @Autowired
    MuscleRepository muscleRepository;

    @Override
    public void run(String... args) {
        // Define muscle groups
        MuscleGroup chest = MuscleGroup.builder().name("Chest").build();
        MuscleGroup shoulders = MuscleGroup.builder().name("Shoulders").build();
        MuscleGroup triceps = MuscleGroup.builder().name("Triceps").build();
        MuscleGroup dorsal = MuscleGroup.builder().name("Dorsal").build();
        MuscleGroup trapeziusAndRhomboids = MuscleGroup.builder().name("Trapezius & rhomboids").build();
        MuscleGroup bicepsAndBrachial = MuscleGroup.builder().name("Biceps & brachial").build();
        MuscleGroup forearms = MuscleGroup.builder().name("Forearms").build();

// Muscles – version builder() + build()
        Muscle pectoralisMajorClavicularHead = Muscle.builder()
                .name("Clavicular head of the pectoralis major")
                .muscleGroup(chest)
                .build();
        Muscle pectoralisMajorSternalHead = Muscle.builder()
                .name("Sternal head of the pectoralis major")
                .muscleGroup(chest)
                .build();
        Muscle pectoralisMinor = Muscle.builder()
                .name("Pectoralis minor")
                .muscleGroup(chest)
                .build();

        Muscle frontDeltoid = Muscle.builder()
                .name("Front deltoid")
                .muscleGroup(shoulders)
                .build();
        Muscle lateralDeltoid = Muscle.builder()
                .name("Lateral deltoid")
                .muscleGroup(shoulders)
                .build();
        Muscle rearDeltoid = Muscle.builder()
                .name("Rear deltoid")
                .muscleGroup(shoulders)
                .build();

        Muscle longHeadOfTheTriceps = Muscle.builder()
                .name("Long head of the triceps")
                .muscleGroup(triceps)
                .build();
        Muscle lateralHeadOfTheTriceps = Muscle.builder()
                .name("Lateral head of the triceps")
                .muscleGroup(triceps)
                .build();
        Muscle medialHeadOfTheTriceps = Muscle.builder()
                .name("Medial head of the triceps")
                .muscleGroup(triceps)
                .build();

        Muscle latissimusDorsi = Muscle.builder()
                .name("Latissimus dorsi")
                .muscleGroup(dorsal)
                .build();
        Muscle teresMajor = Muscle.builder()
                .name("Teres major")
                .muscleGroup(dorsal)
                .build();
        Muscle teresMinor = Muscle.builder()
                .name("Teres minor")
                .muscleGroup(dorsal)
                .build();

        Muscle upperTrapezius = Muscle.builder()
                .name("Upper trapezius")
                .muscleGroup(trapeziusAndRhomboids)
                .build();
        Muscle middleTrapezius = Muscle.builder()
                .name("Middle trapezius")
                .muscleGroup(trapeziusAndRhomboids)
                .build();
        Muscle lowerTrapezius = Muscle.builder()
                .name("Lower trapezius")
                .muscleGroup(trapeziusAndRhomboids)
                .build();
        Muscle rhomboidMajor = Muscle.builder()
                .name("Rhomboid major")
                .muscleGroup(trapeziusAndRhomboids)
                .build();
        Muscle rhomboidMinor = Muscle.builder()
                .name("Rhomboid minor")
                .muscleGroup(trapeziusAndRhomboids)
                .build();

        Muscle longHeadOfBiceps = Muscle.builder()
                .name("Long head of the biceps brachii")
                .muscleGroup(bicepsAndBrachial)
                .build();
        Muscle shortHeadOfBiceps = Muscle.builder()
                .name("Short head of the biceps brachii")
                .muscleGroup(bicepsAndBrachial)
                .build();
        Muscle brachialis = Muscle.builder()
                .name("Brachialis")
                .muscleGroup(bicepsAndBrachial)
                .build();

        Muscle brachioradialis = Muscle.builder()
                .name("Brachioradialis")
                .muscleGroup(forearms)
                .build();
        Muscle flexorCarpiRadialis = Muscle.builder()
                .name("Flexor carpi radialis")
                .muscleGroup(forearms)
                .build();
        Muscle flexorCarpiUlnaris = Muscle.builder()
                .name("Flexor carpi ulnaris")
                .muscleGroup(forearms)
                .build();
        Muscle flexorDigitorumSuperficialis = Muscle.builder()
                .name("Flexor digitorum superficialis")
                .muscleGroup(forearms)
                .build();
        Muscle extensorCarpiRadialisLongus = Muscle.builder()
                .name("Extensor carpi radialis longus")
                .muscleGroup(forearms)
                .build();
        Muscle extensorCarpiUlnaris = Muscle.builder()
                .name("Extensor carpi ulnaris")
                .muscleGroup(forearms)
                .build();

        // Save MuscleGroups first (with cascade if set), then muscles
        muscleGroupRepository.saveAll(Arrays.asList(
                chest, shoulders, triceps, dorsal,
                trapeziusAndRhomboids, bicepsAndBrachial, forearms
        ));

        // Save all muscles
        muscleRepository.saveAll(Arrays.asList(
                pectoralisMajorClavicularHead, pectoralisMajorSternalHead, pectoralisMinor,
                frontDeltoid, lateralDeltoid, rearDeltoid,
                longHeadOfTheTriceps, lateralHeadOfTheTriceps, medialHeadOfTheTriceps,
                latissimusDorsi, teresMajor, teresMinor,
                upperTrapezius, middleTrapezius, lowerTrapezius, rhomboidMajor, rhomboidMinor,
                longHeadOfBiceps, shortHeadOfBiceps, brachialis,
                brachioradialis, flexorCarpiRadialis, flexorCarpiUlnaris, flexorDigitorumSuperficialis, extensorCarpiRadialisLongus, extensorCarpiUlnaris
        ));

        // Create exercises
        Exercice benchPress = Exercice.builder()
                .name("Bench press")
                .description("Classic chest exercise.")
                .referenceMuscleGroups(List.of(chest))
                .primaryMuscleGroups(List.of(chest, shoulders, triceps))
                .secondaryMuscleGroups(List.of(dorsal, trapeziusAndRhomboids))
                .specificMusclesInGroup(List.of(frontDeltoid))
                .build();

        Exercice wideGripPullUp = Exercice.builder()
                .name("Wide grip Pull-up")
                .description("Pull-up with wide grip targeting back.")
                .referenceMuscleGroups(List.of(dorsal))
                .primaryMuscleGroups(List.of(dorsal, bicepsAndBrachial, trapeziusAndRhomboids))
                .secondaryMuscleGroups(List.of(triceps, forearms, shoulders))
                .specificMusclesInGroup(List.of(longHeadOfTheTriceps, rearDeltoid))
                .build();

        exerciceRepository.saveAll(List.of(benchPress, wideGripPullUp));
    }
}
