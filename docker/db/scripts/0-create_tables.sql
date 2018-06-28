--
-- TOC entry 197 (class 1259 OID 32786)
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    description character varying(140) NOT NULL,
    name character varying(60) NOT NULL,
    parent_id bigint
);


--
-- TOC entry 196 (class 1259 OID 32784)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2947 (class 0 OID 0)
-- Dependencies: 196
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- TOC entry 198 (class 1259 OID 32792)
-- Name: category_project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category_project (
    category_id bigint NOT NULL,
    project_id bigint NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 32799)
-- Name: comment; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.comment (
    id bigint NOT NULL,
    date_time timestamp without time zone NOT NULL,
    message text NOT NULL,
    author_id bigint,
    project_id bigint
);


--
-- TOC entry 199 (class 1259 OID 32797)
-- Name: comment_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.comment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2948 (class 0 OID 0)
-- Dependencies: 199
-- Name: comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.comment_id_seq OWNED BY public.comment.id;


--
-- TOC entry 202 (class 1259 OID 32810)
-- Name: contribution; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.contribution (
    id bigint NOT NULL,
    date_time timestamp without time zone NOT NULL,
    money_currency character varying(3) NOT NULL,
    money_amount numeric(19,2) NOT NULL,
    payment_id character varying(255),
    contributor_id bigint,
    project_id bigint,
    reward_id bigint
);


--
-- TOC entry 201 (class 1259 OID 32808)
-- Name: contribution_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.contribution_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2949 (class 0 OID 0)
-- Dependencies: 201
-- Name: contribution_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.contribution_id_seq OWNED BY public.contribution.id;


--
-- TOC entry 204 (class 1259 OID 32818)
-- Name: faq; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.faq (
    id bigint NOT NULL,
    answer character varying(140) NOT NULL,
    question character varying(140) NOT NULL,
    project_id bigint
);


--
-- TOC entry 203 (class 1259 OID 32816)
-- Name: faq_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.faq_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2950 (class 0 OID 0)
-- Dependencies: 203
-- Name: faq_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.faq_id_seq OWNED BY public.faq.id;


--
-- TOC entry 206 (class 1259 OID 32826)
-- Name: message; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.message (
    id bigint NOT NULL,
    date_time timestamp without time zone NOT NULL,
    message text NOT NULL,
    receiver_id bigint,
    sender_id bigint
);


--
-- TOC entry 205 (class 1259 OID 32824)
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2951 (class 0 OID 0)
-- Dependencies: 205
-- Name: message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.message_id_seq OWNED BY public.message.id;


--
-- TOC entry 208 (class 1259 OID 32837)
-- Name: notification; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.notification (
    dtype character varying(31) NOT NULL,
    id bigint NOT NULL,
    date_time timestamp without time zone NOT NULL,
    message character varying(255) NOT NULL,
    project_id bigint,
    receiver_id bigint,
    update_id bigint,
    contribution_id bigint,
    person_id bigint
);


--
-- TOC entry 207 (class 1259 OID 32835)
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2952 (class 0 OID 0)
-- Dependencies: 207
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.notification_id_seq OWNED BY public.notification.id;


--
-- TOC entry 210 (class 1259 OID 32845)
-- Name: person; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.person (
    id bigint NOT NULL,
    city character varying(64),
    country character varying(64),
    email character varying(255) NOT NULL,
    name character varying(64) NOT NULL,
    password character varying(255) NOT NULL,
    image_link character varying(255),
    role character varying(255) NOT NULL,
    surname character varying(64)
);


--
-- TOC entry 211 (class 1259 OID 32854)
-- Name: person_follower; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.person_follower (
    follower_id bigint NOT NULL,
    followed_id bigint NOT NULL
);


--
-- TOC entry 209 (class 1259 OID 32843)
-- Name: person_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.person_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2953 (class 0 OID 0)
-- Dependencies: 209
-- Name: person_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.person_id_seq OWNED BY public.person.id;


--
-- TOC entry 212 (class 1259 OID 32859)
-- Name: person_project_subscription; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.person_project_subscription (
    person_id bigint NOT NULL,
    project_id bigint NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 32864)
-- Name: person_project_team; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.person_project_team (
    person_id bigint NOT NULL,
    project_id bigint NOT NULL
);


--
-- TOC entry 215 (class 1259 OID 32871)
-- Name: project; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.project (
    id bigint NOT NULL,
    description text,
    end_date_time timestamp without time zone,
    event_date_time timestamp without time zone,
    funding_goal_currency character varying(3),
    funding_goal_amount numeric(19,2),
    location_latitude double precision,
    location_longitude double precision,
    name character varying(60) NOT NULL,
    payment_account_id character varying(255),
    image_link character varying(255),
    short_description character varying(140) NOT NULL,
    start_date_time timestamp without time zone,
    type character varying(255) NOT NULL,
    visibility character varying(255) NOT NULL,
    owner_id bigint
);


--
-- TOC entry 214 (class 1259 OID 32869)
-- Name: project_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.project_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2954 (class 0 OID 0)
-- Dependencies: 214
-- Name: project_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.project_id_seq OWNED BY public.project.id;


--
-- TOC entry 216 (class 1259 OID 32880)
-- Name: project_image; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.project_image (
    project_id bigint NOT NULL,
    image_link character varying(255)
);


--
-- TOC entry 217 (class 1259 OID 32883)
-- Name: project_video; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.project_video (
    project_id bigint NOT NULL,
    video_link character varying(255)
);


--
-- TOC entry 219 (class 1259 OID 32888)
-- Name: reward; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reward (
    id bigint NOT NULL,
    delivery_date character varying(140),
    description character varying(140) NOT NULL,
    reward_limit integer,
    minimal_contribution_currency character varying(3) NOT NULL,
    minimal_contribution_amount numeric(19,2) NOT NULL,
    shipping_location character varying(140),
    project_id bigint
);


--
-- TOC entry 218 (class 1259 OID 32886)
-- Name: reward_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.reward_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2955 (class 0 OID 0)
-- Dependencies: 218
-- Name: reward_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.reward_id_seq OWNED BY public.reward.id;


--
-- TOC entry 221 (class 1259 OID 32896)
-- Name: update; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.update (
    id bigint NOT NULL,
    date_time timestamp without time zone NOT NULL,
    message text NOT NULL,
    short_message character varying(140),
    title character varying(60) NOT NULL,
    project_id bigint
);


--
-- TOC entry 220 (class 1259 OID 32894)
-- Name: update_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.update_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2956 (class 0 OID 0)
-- Dependencies: 220
-- Name: update_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.update_id_seq OWNED BY public.update.id;


--
-- TOC entry 2753 (class 2604 OID 32789)
-- Name: category id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- TOC entry 2754 (class 2604 OID 32802)
-- Name: comment id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment ALTER COLUMN id SET DEFAULT nextval('public.comment_id_seq'::regclass);


--
-- TOC entry 2755 (class 2604 OID 32813)
-- Name: contribution id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contribution ALTER COLUMN id SET DEFAULT nextval('public.contribution_id_seq'::regclass);


--
-- TOC entry 2756 (class 2604 OID 32821)
-- Name: faq id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.faq ALTER COLUMN id SET DEFAULT nextval('public.faq_id_seq'::regclass);


--
-- TOC entry 2757 (class 2604 OID 32829)
-- Name: message id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message ALTER COLUMN id SET DEFAULT nextval('public.message_id_seq'::regclass);


--
-- TOC entry 2758 (class 2604 OID 32840)
-- Name: notification id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification ALTER COLUMN id SET DEFAULT nextval('public.notification_id_seq'::regclass);


--
-- TOC entry 2759 (class 2604 OID 32848)
-- Name: person id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person ALTER COLUMN id SET DEFAULT nextval('public.person_id_seq'::regclass);


--
-- TOC entry 2760 (class 2604 OID 32874)
-- Name: project id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project ALTER COLUMN id SET DEFAULT nextval('public.project_id_seq'::regclass);


--
-- TOC entry 2761 (class 2604 OID 32891)
-- Name: reward id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reward ALTER COLUMN id SET DEFAULT nextval('public.reward_id_seq'::regclass);


--
-- TOC entry 2762 (class 2604 OID 32899)
-- Name: update id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.update ALTER COLUMN id SET DEFAULT nextval('public.update_id_seq'::regclass);


--
-- TOC entry 2764 (class 2606 OID 32791)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 2766 (class 2606 OID 32796)
-- Name: category_project category_project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_project
    ADD CONSTRAINT category_project_pkey PRIMARY KEY (category_id, project_id);


--
-- TOC entry 2768 (class 2606 OID 32807)
-- Name: comment comment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT comment_pkey PRIMARY KEY (id);


--
-- TOC entry 2770 (class 2606 OID 32815)
-- Name: contribution contribution_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contribution
    ADD CONSTRAINT contribution_pkey PRIMARY KEY (id);


--
-- TOC entry 2772 (class 2606 OID 32823)
-- Name: faq faq_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.faq
    ADD CONSTRAINT faq_pkey PRIMARY KEY (id);


--
-- TOC entry 2774 (class 2606 OID 32834)
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- TOC entry 2776 (class 2606 OID 32842)
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (id);


--
-- TOC entry 2780 (class 2606 OID 32858)
-- Name: person_follower person_follower_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_follower
    ADD CONSTRAINT person_follower_pkey PRIMARY KEY (follower_id, followed_id);


--
-- TOC entry 2778 (class 2606 OID 32853)
-- Name: person person_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person
    ADD CONSTRAINT person_pkey PRIMARY KEY (id);


--
-- TOC entry 2782 (class 2606 OID 32863)
-- Name: person_project_subscription person_project_subscription_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_subscription
    ADD CONSTRAINT person_project_subscription_pkey PRIMARY KEY (person_id, project_id);


--
-- TOC entry 2784 (class 2606 OID 32868)
-- Name: person_project_team person_project_team_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_team
    ADD CONSTRAINT person_project_team_pkey PRIMARY KEY (person_id, project_id);


--
-- TOC entry 2786 (class 2606 OID 32879)
-- Name: project project_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT project_pkey PRIMARY KEY (id);


--
-- TOC entry 2788 (class 2606 OID 32893)
-- Name: reward reward_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT reward_pkey PRIMARY KEY (id);


--
-- TOC entry 2790 (class 2606 OID 32904)
-- Name: update update_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.update
    ADD CONSTRAINT update_pkey PRIMARY KEY (id);


--
-- TOC entry 2811 (class 2606 OID 33005)
-- Name: person_project_team fk16n6038fuf0lc9y7yo739wl9o; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_team
    ADD CONSTRAINT fk16n6038fuf0lc9y7yo739wl9o FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2794 (class 2606 OID 32920)
-- Name: comment fk1ex1hm6r7cy96qsw6yc5xalsq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fk1ex1hm6r7cy96qsw6yc5xalsq FOREIGN KEY (author_id) REFERENCES public.person(id);


--
-- TOC entry 2803 (class 2606 OID 32965)
-- Name: notification fk1gpahgi4dljypje9pdsmhp6wv; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk1gpahgi4dljypje9pdsmhp6wv FOREIGN KEY (receiver_id) REFERENCES public.person(id);


--
-- TOC entry 2792 (class 2606 OID 32910)
-- Name: category_project fk4ufi0p7bcwqn7dag6vmfsan4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_project
    ADD CONSTRAINT fk4ufi0p7bcwqn7dag6vmfsan4 FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2791 (class 2606 OID 32905)
-- Name: category fk5s5t2pfpxo0vnd1ihc43721ty; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT fk5s5t2pfpxo0vnd1ihc43721ty FOREIGN KEY (parent_id) REFERENCES public.category(id);


--
-- TOC entry 2797 (class 2606 OID 32935)
-- Name: contribution fk69nw8t4q6dq1yq160w25xp2se; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contribution
    ADD CONSTRAINT fk69nw8t4q6dq1yq160w25xp2se FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2806 (class 2606 OID 32980)
-- Name: notification fk7prriu34jqhmv72bihojxpuy0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk7prriu34jqhmv72bihojxpuy0 FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- TOC entry 2799 (class 2606 OID 32945)
-- Name: faq fk7s3a6uui0qdcrdixys5r180vl; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.faq
    ADD CONSTRAINT fk7s3a6uui0qdcrdixys5r180vl FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2805 (class 2606 OID 32975)
-- Name: notification fk80nsukgtwycgxu2egkkpxbolq; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk80nsukgtwycgxu2egkkpxbolq FOREIGN KEY (contribution_id) REFERENCES public.contribution(id);


--
-- TOC entry 2804 (class 2606 OID 32970)
-- Name: notification fk86knsvmcx9e28m5lmu6axge5s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fk86knsvmcx9e28m5lmu6axge5s FOREIGN KEY (update_id) REFERENCES public.update(id);


--
-- TOC entry 2798 (class 2606 OID 32940)
-- Name: contribution fk98348l8lo5oia4ddmcsfd6w0s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contribution
    ADD CONSTRAINT fk98348l8lo5oia4ddmcsfd6w0s FOREIGN KEY (reward_id) REFERENCES public.reward(id);


--
-- TOC entry 2795 (class 2606 OID 32925)
-- Name: comment fkad9y3qtjwnl6t3nuwuxthi7bi; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comment
    ADD CONSTRAINT fkad9y3qtjwnl6t3nuwuxthi7bi FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2796 (class 2606 OID 32930)
-- Name: contribution fkafdxxm5d9kp02vgcqu7112ob0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.contribution
    ADD CONSTRAINT fkafdxxm5d9kp02vgcqu7112ob0 FOREIGN KEY (contributor_id) REFERENCES public.person(id);


--
-- TOC entry 2814 (class 2606 OID 33020)
-- Name: project_image fkawtuqujag5bvkssx9o1lnvt2g; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project_image
    ADD CONSTRAINT fkawtuqujag5bvkssx9o1lnvt2g FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2802 (class 2606 OID 32960)
-- Name: notification fkckpn0md91x7m2pw9ab5wgh9ss; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fkckpn0md91x7m2pw9ab5wgh9ss FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2813 (class 2606 OID 33015)
-- Name: project fkemha2gm79ly75sef5tldnyp0r; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project
    ADD CONSTRAINT fkemha2gm79ly75sef5tldnyp0r FOREIGN KEY (owner_id) REFERENCES public.person(id);


--
-- TOC entry 2801 (class 2606 OID 32955)
-- Name: message fkfnywlixcqqd3epfvtdnncybco; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fkfnywlixcqqd3epfvtdnncybco FOREIGN KEY (sender_id) REFERENCES public.person(id);


--
-- TOC entry 2812 (class 2606 OID 33010)
-- Name: person_project_team fkfr89epdtuj066k5a6t1iescvf; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_team
    ADD CONSTRAINT fkfr89epdtuj066k5a6t1iescvf FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- TOC entry 2816 (class 2606 OID 33030)
-- Name: reward fkgnequ7jxamqvxua62jvqhage8; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reward
    ADD CONSTRAINT fkgnequ7jxamqvxua62jvqhage8 FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2807 (class 2606 OID 32985)
-- Name: person_follower fkip2psktocdllw9rureg7ouuo1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_follower
    ADD CONSTRAINT fkip2psktocdllw9rureg7ouuo1 FOREIGN KEY (followed_id) REFERENCES public.person(id);


--
-- TOC entry 2808 (class 2606 OID 32990)
-- Name: person_follower fkledtwrcwm5cwy55vcgbh5wye; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_follower
    ADD CONSTRAINT fkledtwrcwm5cwy55vcgbh5wye FOREIGN KEY (follower_id) REFERENCES public.person(id);


--
-- TOC entry 2817 (class 2606 OID 33035)
-- Name: update fkltek81lx2d95qv8c0d0htc1k2; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.update
    ADD CONSTRAINT fkltek81lx2d95qv8c0d0htc1k2 FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2809 (class 2606 OID 32995)
-- Name: person_project_subscription fkmc3iqrj0einfsmofipma4i9bp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_subscription
    ADD CONSTRAINT fkmc3iqrj0einfsmofipma4i9bp FOREIGN KEY (project_id) REFERENCES public.project(id);


--
-- TOC entry 2793 (class 2606 OID 32915)
-- Name: category_project fkpvw5uvnqhpkxqcp9l9gnpahcs; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category_project
    ADD CONSTRAINT fkpvw5uvnqhpkxqcp9l9gnpahcs FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- TOC entry 2810 (class 2606 OID 33000)
-- Name: person_project_subscription fkq5otwnjwd80ycqorlpjoq5ss1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.person_project_subscription
    ADD CONSTRAINT fkq5otwnjwd80ycqorlpjoq5ss1 FOREIGN KEY (person_id) REFERENCES public.person(id);


--
-- TOC entry 2800 (class 2606 OID 32950)
-- Name: message fksvaeo98n1ffxs1j845uc5ep1q; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fksvaeo98n1ffxs1j845uc5ep1q FOREIGN KEY (receiver_id) REFERENCES public.person(id);


--
-- TOC entry 2815 (class 2606 OID 33025)
-- Name: project_video fkt3gd39k5uw0q5l9khwi2quv5n; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.project_video
    ADD CONSTRAINT fkt3gd39k5uw0q5l9khwi2quv5n FOREIGN KEY (project_id) REFERENCES public.project(id);
