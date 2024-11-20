# CS7319 Final Project Group 11 - Brown - Pan - Wallace

Our Collaborative Note-Taking App empowers users to create, share, and edit notes seamlessly.

Key features include:

- Secure user login and authentication.
- An dashboard to manage and access notes.
- Create, edit and delete notes.
- (Pub/Sub only) Real-time updates for shared notes, enabling effortless teamwork and collaboration.

## Backend

TODO: Implement this

## Frontend

**Platform**: Node.js >=20.0

**Download**: https://nodejs.org/en/download

    All of the following commands should be written under the assumption that you are in the root git repository.

### Installation / Development

1. Ensure Node.js and npm are installed (see above for download).
2. CD into the `frontend` directory.
3. Run `npm install` to install all required dependencies.
4. To run the development server, execute `npm run dev`.

### Compilation

The web application is built using a Node.js framework called Vite. The source is developed in React, and the output is Javascript, HTML, and CSS.

To build the final output, run the following command:

```
npm run build
```

The final build can be found in the `/dist` directory (relative to `/frontend`).

## Proposal Changes

Our initial proposal included a comparison between a microservices and monolith architecture. Because monolith wasn't covered in class, we opted to switch to a pub/sub approach, which builds upon the REST/microservices architecture we originally discussed. Due to its fast speed and concurrent nature, we believe that adding a pub/sub architecture on top of our existing architecture is a beneficial decision. On top of providing ease-of-use, pub/sub gives us a unique opportunity to discuss very similar, yet distinctly different architectures in one development cycle.