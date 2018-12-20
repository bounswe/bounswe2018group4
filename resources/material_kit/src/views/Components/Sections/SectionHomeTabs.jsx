import React from "react";

// @material-ui/core components
import withStyles from "@material-ui/core/styles/withStyles";

// @material-ui/icons
import Face from "@material-ui/icons/Face";
import Chat from "@material-ui/icons/Chat";
import Build from "@material-ui/icons/Build";
// core components
import GridContainer from "components/Grid/GridContainer.jsx";
import GridItem from "components/Grid/GridItem.jsx";
import CustomTabs from "components/CustomTabs/CustomTabs.jsx";
import tabsStyle from "assets/jss/material-kit-react/views/componentsSections/tabsStyle.jsx";

class SectionTabs extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <div className={classes.section}>
        <div className={classes.container}>
          <div id="nav-tabs">
            <GridContainer>
              <GridItem>
                <h3>
                  <small>The story behind my picture with Tarkan by @batuhan</small>
                </h3>
                <CustomTabs
                  headerColor="primary"
                  tabs={[
                    {
                      tabName: "Story",
                      tabContent: (
                        <p className={classes.textCenter}>
                          I guess I’ve had a lucky childhood considering that not everyone has a nice picture with Tarkan! I remember that my family took me to a dinner with one of their friends. I don’t remember all the details but the restaurant was quite empty on that day and I was getting bored since everyone around me was adult. At some point, I have seen Tarkan approaching to our table. I knew him from Kuzu Kuzu music video.
                          <br />
                          <iframe width="560" height="315" src="https://www.youtube.com/embed/NAHRpEqgcL4" frameBorder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"></iframe>
                          <br />
                          He came since he knew the friend of my parents. Since I was bored I wanted to get the attention of people around me. I started dancing like he did on his music video. He laughed and wanted to take a picture with me.
                          <br />
                          <img src="https://84.gostreamcdn.net/uploads/images/gallery/tarkan_kapak11.JPG" />
                          <br />
                          I felt really cool on that day!
                        </p>
                      )
                    },
                    {
                      tabName: "Location",
                      tabContent: (
                        <p className={classes.textCenter}>
                          Istanbul
                        </p>
                      )
                    },
                    {
                      tabName: "Mentioned Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          2002
                        </p>
                      )
                    },
                    {
                      tabName: "Posted Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          December 2, 2018 17:23
                        </p>
                      )
                    },
                    {
                      tabName: "Tags",
                      tabContent: (
                        <p className={classes.textCenter}>
                          #Tarkan <br />
                          #megastar <br />
                          #kuzukuzu <br />
                          #childhood
                        </p>
                      )
                    },
                  ]}
                />
              </GridItem>
              <GridItem>
                <h3>
                  <small>Best Day of My Life by @Valerie</small>
                </h3>
                <CustomTabs
                  headerColor="primary"
                  tabs={[
                    {
                      tabName: "Story",
                      tabContent: (
                        <p className={classes.textCenter}>
                          When I was between nine and eleven, my parents were at the height of their music careers and were able to take us to a camp on Lake George, New York, for two weeks in the summer, usually around the 4th of July, so we could watch the fireworks over the lake. I remember being dazzled by them. By today's standards, they'd be very modest.
                          <br />
                          We would rent a cabin with a full kitchen, bath and bedrooms, and just live in our bathing suits on a lakeside beach. It was really cozy and smelled of cedar, pine and moss. I would go barefoot the entire time to enjoy the soft moss or cool, slippery pine needles and the soft abrasion of the lakeside sand. As I write this I realize I was quite aware of my toes because this memory is full of foot sensations. My feet, by the way, are much larger than they need to be, and were a source of anxiety in my youth.
                          <br />
                          The place we stayed at had its own beachfront that we shared with other cabin renters, a boathouse with motorboats, canoes, paddle-boats, and indoor games like ping-pong for rainy days. There were plenty of other kids to play with and I was never bored. We often took canoes and paddle-boats out to explore the shoreline. 
                          <br />
                          One day my parents rented a motorboat and packed it up with picnic stuff, including briquettes to do a fire. They took us to a little island that we claimed for ourselves for a day. It had a little cove with an ideal diving rock that was easy to climb atop straight out of the water, so you could just compulsively climb and jump until you wore yourself out like kids do. The water was a deep unfathomable green from which the sun's rays were reflected back in spinning golden beams. There was no danger of hitting the lake floor here, and we were good swimmers. In the picture below, we are groovin' on it:
                          <br /> 
                          <img src="https://qph.fs.quoracdn.net/main-qimg-905cf0565948a530b4316e2ae558afb8.webp" />
                          <br /> 
                          While we were working up an appetite, our parents were making a fire for our hot dogs. I don't remember hamburgers, and hot dogs were one of the few things Marc would eat. Well, you can't skewer a hamburger on a stick, can you? After we ate, we were not allowed in the water until we had digested lunch awhile so we explored the island. That's my mom and my stepbrother, Marc. She would have been 29 or so. She was so beautiful and strong at this
                          point in her life. Marc was hungry.
                           <br /> 
                          <img src="https://qph.fs.quoracdn.net/main-qimg-ba73fcc88d4b6786fcee8d06877ae3fc.webp" />
                          <br /> 
                          On one end of the island were large rocks on which we sat with our feet in the water and watched sunfish nibble our toes, and giggled hysterically. That is my favorite part of childhood: the giggles that you cannot stop. I used to make my stepbrother giggle on purpose because after a while, he'd get the hiccups that, combined with his laughter, resulted in funny belching sounds, which further fueled my giggle fit, and the giggle contagion would continue until we were a wreck and he'd be begging me to stop. I'd try to contain myself but we'd look at each other and I would fail time and again, exploding in another fit of giggles.
                          <br /> 
                          We would forget about the sun and get a little burnt. Back then, we weren't worried about the ozone layer or ultraviolet rays. So far, I have escaped getting a skin cancer diagnosis, although I had been sunburned to the point of peeling many times when I was a girl. I used to have freckles.
                          <br /> 
                          On the way back from the island, I think my mom took a picture of my stepbrother and I -- my stepfather usually navigated the boat. It was taken with a Polariod camera -- all the rage then because it gave you instant gratification. You aimed, pressed a button, the camera spit out the print, you peeled it apart, and you could shake it, even though that didn't really make it develop or dry faster. After my stepbrother and I looked at this photograph, we both fell off our seat and had another giggle attack.
                          <br /> 
                          <img src="https://qph.fs.quoracdn.net/main-qimg-665ecaa250f80abf0d745209b13987ca.webp" />
                          <br /> 
                          I remember this day with such clarity because it was probably the most perfect day in my life.
                        </p>
                      )
                    },
                    {
                      tabName: "Location",
                      tabContent: (
                        <p className={classes.textCenter}>
                          Lake George, New York
                        </p>
                      )
                    },
                    {
                      tabName: "Mentioned Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          July 4th
                        </p>
                      )
                    },
                    {
                      tabName: "Posted Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          October 8, 2018 17:23
                        </p>
                      )
                    },
                    {
                      tabName: "Tags",
                      tabContent: (
                        <p className={classes.textCenter}>
                          #Childhood 
                        </p>
                      )
                    },
                  ]}
                />
              </GridItem>
              <GridItem>
                <h3>
                  <small>Day One by @cemalaytekin</small>
                </h3>
                <CustomTabs
                  headerColor="primary"
                  tabs={[
                    {
                      tabName: "Story",
                      tabContent: (
                        <p className={classes.textCenter}>
                          I was a lovely day.. We had just got out of preparation exam called PROFICIENCY. It was the first day of summer and we planned a night we can spend on the sands of Kilyos. We torched a light and we played guitar, we sang together. I had the chance to speak with her first time in that night. Until the dawn... So, Kilyos has a special meaning for us. If you stay in there for your preparation year as I did, do not forget that what happens in Kilyos stays in Kilyos except the love...
                          <img src="http://www.kampushaber.com/images/haberler/pinhani-sevilen-sarkilariyla-bogazici-kilyos-kampusu-nde.jpg" alt="Smiley face" />
                        </p>
                      )
                    },
                    {
                      tabName: "Location",
                      tabContent: (
                        <p className={classes.textCenter}>
                          Kilyos
                        </p>
                      )
                    },
                    {
                      tabName: "Mentioned Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          June 1st, 2016
                        </p>
                      )
                    },
                    {
                      tabName: "Posted Time",
                      tabContent: (
                        <p className={classes.textCenter}>
                          October 20, 2018 17:23
                        </p>
                      )
                    },
                    {
                      tabName: "Tags",
                      tabContent: (
                        <p className={classes.textCenter}>
                          #FirstLove <br />
                          #MyDearKilyos
                        </p>
                      )
                    },
                  ]}
                />
              </GridItem>
            </GridContainer>
          </div>
        </div>
      </div>
    );
  }
}

export default withStyles(tabsStyle)(SectionTabs);
