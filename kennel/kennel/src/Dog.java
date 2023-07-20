// Max Holm maho6364

import java.util.List;

public class Dog {

    private String name;
    private int age;
    private String breed;
    private int weight;

    public Dog(String name, String breed, int age, int weight) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public String getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public double getTailLength() {
        if (isDachsHound())
            return 3.7;
        return (double) this.age * this.weight / 10;
    }

    @Override
    public String toString() {
        // This will return a string representation of the dog
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + this.name);
        sb.append(" age: " + this.age);
        sb.append(" breed: " + this.breed);
        sb.append(" weight: " + this.weight);
        sb.append(" tail length: " + getTailLength());
        return sb.toString();
    }

    public void updateAge(int ageIncrease) {
        if (ageIncrease > 0)
            this.age = this.age + ageIncrease;
    }

    private boolean isDachsHound() {
        // checks if dog breed is dachshound
        switch (this.breed.toLowerCase()) {
            case "tax":
                return true;
            case "dachshund":
                return true;
            case "teckel":
                return true;
            default:
                return false;
        }
    }
    public static Dog findDogByName(List<Dog> dogList, String name) {
        for (Dog dog : dogList) {
            if (dog.getName().equalsIgnoreCase(name)) {
                return dog;
            }
        }
        return null;
    }
}