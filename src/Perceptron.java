public class Perceptron {
    private double[] weights;
    private double learningRate = 0.1;

    public Perceptron(int weightsLength) {
        weights = new double[weightsLength];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random() * 2 - 1;
        }
    }

    public int guess(double[] inputs) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += inputs[i] * weights[i];
        }
        return sum >= 0 ? 1 : -1;
    }

    public void train(double[] inputs, int target) {
        int error = target - guess(inputs);
        if (error != 0) {
            for (int i = 0; i < weights.length; i++) {
                weights[i] += error * inputs[i] * learningRate;
            }
        }
    }
}
