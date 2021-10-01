#!/usr/bin/env python

import fire
import jenkins
import time
import json
# https://myopswork.com/trigger-multiple-jenkins-jobs-from-json-api-with-python-script-244ea85557a9
# https://wiki.jenkins.io/display/JENKINS/Remote+access+API/


# class JenBuild:


class JenCli:

    def __init__(self):
        self.server = jenkins.Jenkins('http://jencondadev.samexample.com:8080', username='tester', password='tester123')

    def info_sample(self):
        server = self.server

        user = server.get_whoami()
        version = server.get_version()
        print('Hello %s from Jenkins %s' % (user['fullName'], version))

    def build_bootstrap(self):
        server = self.server
        job_name = '_new_ci'
        build_number = server.build_job(job_name, parameters=dict(
            seed_job_branch='master',
            seed_job_name='_auto_seed')
        )

    def build_job_block(self, j_name, parameters=None, show_log=False):
        # https://stackoverflow.com/questions/24313471/python-jenkins-get-job-info-how-do-i-get-info-on-more-than-100-builds
        server = self.server
        ps = {}
        if parameters is not None:
            ps['parameters'] = parameters

        next_build_number = server.get_job_info(j_name)['nextBuildNumber']
        gbuild = server.build_job(j_name, **ps)
        while True:
            print(f'Waiting....{gbuild} as {next_build_number} for {j_name}')
            try:
                if server.get_job_info(j_name)['lastCompletedBuild']['number'] == server.get_job_info(j_name)['lastBuild']['number']:
                    print ("Last ID %s, Current ID %s"  % (server.get_job_info(j_name)['lastCompletedBuild']['number'], server.get_job_info(j_name)['lastBuild']['number']))
                    break
            except:
                pass
            time.sleep(2)
        print('Stop....')
        last_number = server.get_job_info(j_name)['lastBuild']['number']
        jobinfo = server.get_build_info(j_name, last_number)
        _ret = {
            'jobinfo': jobinfo,
            'gbuild': gbuild,
            'jobname': j_name,
            'last_number': last_number,

            'console_cmd': f'server get_build_console_output {j_name} {last_number}'
        }
        if show_log:
            pet ={}
            pet['console_log'] = server.get_build_console_output(j_name, last_number)
            print(pet['console_log'])
        # print(_ret)
        return _ret

    def build_sample(self, show_log=False):
        return self.build_job_block('samples/pipe_501_cmds_explicit.groovy', show_log=show_log)

    def test_bootstrap(self,show_log=1):
        print("asdf")
        server = self.server
        import subprocess
        dt = subprocess.check_output(["date","+%Y%m%d.%H%M%S"]).decode().strip()


        import waiting
        waiting.wait(
            lambda: server.get_job_info('_new_ci'),
            timeout_seconds=60,
            expected_exceptions=(jenkins.JenkinsException,)
        )

        # seed_job_branch = subprocess.check_output(
        #     "git -C /home/jovyan/repo rev-parse --abbrev-ref HEAD".split()
        #     ).decode().strip() 
        seed_job_branch = 'yairdar.v0.8.3.pushes'
        seed_job = f'_test_seed_{dt}'
        seed_job_path = 'file:///repo'
        seed_job_pipe = "data4test/gitlab_seeds/jobs-registrator.pipe.groovy"
        jobs_def_dir = "data4test/gitlab_seeds/jobs-defs"

		# stringParam("seed_job_name", "_seed_job", "seed_job_name")
		# stringParam("seed_job_path", "file:///repo", "seed_job_path")
		# stringParam("seed_job_pipe", "data4test/gitlab_seeds/jobs-registrator.pipe.groovy", "seed_job_pipe")
		# stringParam("seed_job_branch", "master", "seed_job_branch")
		# stringParam("jobs_def_dir", "data4test/gitlab_seeds/jobs-defs", "jobs_def_dir")

        _ret = self.build_job_block(
            '_new_ci',
            parameters=dict(
                seed_job_branch=seed_job_branch,
                seed_job_path=seed_job_path,
                jobs_def_dir=jobs_def_dir,
                seed_job_pipe=seed_job_pipe,
                seed_job_name=seed_job),
            show_log=show_log,
        )
        assert 'SUCCESS' == _ret['jobinfo']['result'], json.dumps(_ret, indent=2, sort_keys=True)

        # wait until job is created
        print("@@act=wait stage=start topic='job created'")
        waiting.wait(
            lambda: server.get_job_info(seed_job),
            timeout_seconds=60,
            expected_exceptions=(jenkins.JenkinsException,)
        )
        print("@@act=wait stage=over topic='job created'")
        time.sleep(3)
        _ret = self.build_job_block(seed_job, parameters={
            "seed_job_repo": seed_job_path,
            "seed_job_branch": seed_job_branch,
        }, show_log=show_log)
        assert 'SUCCESS' == _ret['jobinfo']['result'], json.dumps(_ret, indent=2, sort_keys=True)

        print("@@act=init stage=build.samples")
        _ret = self.build_job_block('samples/pipe_503_tasks_from_yml.groovy', show_log=show_log)
        assert 'SUCCESS' == _ret['jobinfo']['result'], json.dumps(_ret, indent=2, sort_keys=True)

if __name__ == '__main__':
    # jc = JenCli()
    # jc.build_sample()
    fire.Fire(JenCli)
