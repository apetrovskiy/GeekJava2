package com.geek.java2.lesson05;

import java.util.Arrays;

/*
1. Необходимо написать два метода, которые делают следующее:
1) Создают одномерный длинный массив, например:
static final int size = 10000000;
static final int h = size / 2;
float[] arr = new float[size];
2) Заполняют этот массив единицами;
3) Засекают время выполнения: long a = System.currentTimeMillis();
4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
5) Проверяется время окончания метода System.currentTimeMillis();
6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
Отличие первого метода от второго:
Первый просто бежит по массиву и вычисляет значения.
Второй разбивает массив на два массива, в двух потоках высчитывает новые значения
и потом склеивает эти массивы обратно в один.

Пример деления одного массива на два:
System.arraycopy(arr, 0, a1, 0, h);
System.arraycopy(arr, h, a2, 0, h);

Пример обратной склейки:
System.arraycopy(a1, 0, arr, 0, h);
System.arraycopy(a2, 0, arr, h, h);

Примечание:
System.arraycopy() копирует данные из одного массива в другой:
System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение,
откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
По замерам времени:
Для первого метода надо считать время только на цикл расчета:
for (int i = 0; i < size; i++) {
arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
}
Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
*/
public class HomeworkRunner {
    private static final float VALUE = 1f;
    private static final int SIZE = 10000000;
    private static final int HALF = SIZE / 2;

    public static void method01() {
        float[] array = setUp();
        long start = System.currentTimeMillis();
        changeArray(array);
        outTime("method01", start);
    }

    public static void method02() {
        float[] array = setUp();
        float[] a1 = new float[HALF];
        float[] a2 = new float[HALF];

        long start = System.currentTimeMillis();

        // Пример деления одного массива на два:
        System.arraycopy(array, 0, a1, 0, HALF);
        System.arraycopy(array, HALF, a2, 0, HALF);
        a1 = changeArray(a1);
        a2 = changeArray(a2);

        // Пример обратной склейки:
        System.arraycopy(a1, 0, array, 0, HALF);
        System.arraycopy(a2, 0, array, HALF, HALF);


        outTime("method02", start);
    }

    private static float[] setUp() {
        float[] array = new float[SIZE];
        Arrays.fill(array, VALUE);
        return array;
    }

    private static float[] changeArray(float[] array) {
        Runnable changeArrayClass = () -> {
            for (int i = 0; i < array.length; i++) {
                array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        };
        // changeArrayClass.run();
        new Thread (changeArrayClass). start ();
        return array;
    }

    private static void outTime(String methodName,long startTime) {
        System.out.println(String.format("method %s, time = %s", methodName, System.currentTimeMillis() - startTime));
    }
}
