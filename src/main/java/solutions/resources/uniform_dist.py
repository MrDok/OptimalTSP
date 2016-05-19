import numpy as np

if __name__ == "__main__":
    f = open("uniform90.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 90)
        np.savetxt(f, s, fmt='%.3f')
    f.close()

    f = open("uniform132.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 132)
        np.savetxt(f, s, fmt='%.3f')
    f.close()

    f = open("uniform182.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 182)
        np.savetxt(f, s, fmt='%.3f')
    f.close()

    f = open("uniform240.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 240)
        np.savetxt(f, s, fmt='%.3f')
    f.close()

    f = open("uniform306.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 306)
        np.savetxt(f, s, fmt='%.3f')
    f.close()

    f = open("uniform380.txt", "ab")
    for x in range(0,100):
        s = np.random.uniform(0, 1, 380)
        np.savetxt(f, s, fmt='%.3f')
    f.close()