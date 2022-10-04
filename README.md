# R - Corn (Uni-Type?)
## A Ludum Dare 51 Jam entry

There's a unicorn and a spaceship, and one day...er...they combined forces to do something. And that something was defeat a cross-dimensional evil or something. Yeah, that's right! You are that spaceship/unicorn. Oh, and the unicorn can fly.

### Controls
- Space - flap/shoot
- WASD - move

### Credits
- Kerrindor: Graphics, music, sound, ~~code~~ bugs
- Yskuma: Code, Backgrounds, BRAAAM
- Spawn of Yskuma (12): RAINBOWS!!!!

### Tech stuff
- Written in Java using libGDX. 
- Graphics in Aseprite, GIMP and Inkscape.
- Audio in LMMS and Sfxr.
- The clever bit was only really possible because we were making use of the Ashley ECS library, so we can swap behaviours in and out easily.

### Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `html:dist`: compiles GWT sources. The compiled application can be found at `html/build/dist`: you can use any HTTP server to deploy it.
- `html:superDev`: compiles GWT sources and runs the application in SuperDev mode. It will be available at [localhost:8080/html](http://localhost:8080/html). Use only during development.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
