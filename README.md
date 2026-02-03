# CIST4B1_AdvJava
A repository to document my Java learning journey.

About Me:
My name is Ashmit, and I am a junior at Lynbrook High School in the Bay Area. I have been interested in coding since middle school, and have 4 years of coding experience, including completing AP CS-A in school last year. I am familiar with Python, Java, and C++. One of my hobbies is reading books regarding thought processes and the way things work; "How Not to Be Wrong" by Jordan Ellenburg and "Freakonomics" by Steven Levitt are personal favorites. I also enjoy watching soccer, football, and cricket in my spare time.

Goals for This Course:
I would like to delve deeper into data structures and algorithms, while improving my Java knowledge. I would like to understand the most frequently used algorithms in daily life, such as sorting, searching, etc. Further, I would like to understand how to eventually scale my algorithms for runtime and memory efficiency.

In-Class Java Coding Warm Up file:

public class Animal
{
    public void makeNoise()
    {
        System.out.println("Hi");
    }
}

class Lion extends Animal
{
    private String name;
    private int weight;

    public Lion(String name, int weight)
    {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public void makeNoise()
    {
        System.out.println("Roar");
    }

    public String getName()
    {
        return name;
    }

    public int getWeight()
    {
        return weight;
    }
}

class Dog extends Animal
{
    private String breed;
    private int age;

    public Dog(String breed, int age)
    {
        this.breed = breed;
        this.age = age;
    }

    @Override
    public void makeNoise()
    {
        System.out.println("Woof");
    }

    public String getBreed()
    {
        return breed;
    }
    
    public int getAge()
    {
        return age;
    }
}

class Main
{
    public static void main(String[] args)
    {
        Animal a1 = new Lion("Simba", 500);
        Animal a2 = new Dog("Labrador", 5);
        Animal a3 = new Dog("Poodle", 3);

        a1.makeNoise();
        a2.makeNoise();
        a3.makeNoise();
    }
}
